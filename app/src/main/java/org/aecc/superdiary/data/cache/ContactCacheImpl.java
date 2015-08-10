package org.aecc.superdiary.data.cache;

import android.content.Context;

import org.aecc.superdiary.data.cache.serializer.JSONSerializer;
import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.data.exception.ContactNotFoundException;
import org.aecc.superdiary.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

public class ContactCacheImpl implements ContactCache {

    private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "user_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JSONSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    @Inject
    public ContactCacheImpl(Context context, JSONSerializer serializer, ThreadExecutor threadExecutor, FileManager filemanager){
        this.context = context;
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.threadExecutor = threadExecutor;
        this.fileManager = filemanager;
    }

    @Override
    public synchronized void get(int contactId, ContactCache.ContactCacheCallback callback) {
        File contactEntitiyFile = this.buildFile(contactId);
        String fileContent = this.fileManager.readFileContent(contactEntitiyFile);
        ContactEntity contactEntity = this.serializer.deserializeContact(fileContent);

        if (contactEntity != null) {
            callback.onContactEntityLoaded(contactEntity);
        } else {
            callback.onError(new ContactNotFoundException());
        }
    }

    @Override
    public synchronized void put(ContactEntity contactEntity) {
        if (contactEntity != null) {
            File contactEntitiyFile = this.buildFile(contactEntity.getContactId());
            if (!isCached(contactEntity.getContactId())) {
                String jsonString = this.serializer.serializeContact(contactEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, contactEntitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int contactId) {
        File contactEntitiyFile = this.buildFile(contactId);
        return this.fileManager.exists(contactEntitiyFile);
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

    private File buildFile(int contactId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(contactId);

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

        @Override public void run() {
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

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }

}