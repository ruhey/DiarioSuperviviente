package org.aecc.superdiary.data.cache;

import android.content.Context;

import org.aecc.superdiary.data.cache.serializer.JSONSerializer;
import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.data.exception.MedicineNotFoundException;
import org.aecc.superdiary.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

public class MedicineCacheImpl implements MedicineCache {

    private static final String SETTINGS_FILE_NAME = "org.aecc.superdiary.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "medicine_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JSONSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    @Inject
    public MedicineCacheImpl(Context context, JSONSerializer serializer, ThreadExecutor threadExecutor, FileManager filemanager) {
        this.context = context;
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.threadExecutor = threadExecutor;
        this.fileManager = filemanager;
    }

    @Override
    public synchronized void get(int medicineId, MedicineCacheCallback callback) {
        File medicineEntitiyFile = this.buildFile(medicineId);
        String fileContent = this.fileManager.readFileContent(medicineEntitiyFile);
        MedicineEntity medicineEntity = this.serializer.deserializeMedicine(fileContent);

        if (medicineEntity != null) {
            callback.onMedicineEntityLoaded(medicineEntity);
        } else {
            callback.onError(new MedicineNotFoundException());
        }
    }

    @Override
    public synchronized void put(MedicineEntity medicineEntity) {
        if (medicineEntity != null) {
            File medicineEntitiyFile = this.buildFile(medicineEntity.getMedicineId());
            if (!isCached(medicineEntity.getMedicineId())) {
                String jsonString = this.serializer.serializeMedicine(medicineEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, medicineEntitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int medicineId) {
        File medicineEntitiyFile = this.buildFile(medicineId);
        return this.fileManager.exists(medicineEntitiyFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public synchronized void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    private File buildFile(int medicineId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(medicineId);

        return new File(fileNameBuilder.toString());
    }

    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }

}