package org.aecc.superdiary.data.cache;


import android.content.Context;

import org.aecc.superdiary.data.cache.serializer.JSONSerializer;
import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.data.exception.CommentNotFoundException;
import org.aecc.superdiary.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

public class CommentCacheImpl implements CommentCache {

    private static final String SETTINGS_FILE_NAME = "org.aecc.superdiary.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "comment_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JSONSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    @Inject
    public CommentCacheImpl(Context context, JSONSerializer serializer, ThreadExecutor threadExecutor, FileManager filemanager) {
        this.context = context;
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.threadExecutor = threadExecutor;
        this.fileManager = filemanager;
    }

    @Override
    public synchronized void get(int commentId, CommentCache.CommentCacheCallback callback) {
        File commentEntitiyFile = this.buildFile(commentId);
        String fileContent = this.fileManager.readFileContent(commentEntitiyFile);
        CommentEntity commentEntity = this.serializer.deserializeComment(fileContent);

        if (commentEntity != null) {
            callback.onCommentEntityLoaded(commentEntity);
        } else {
            callback.onError(new CommentNotFoundException());
        }
    }

    @Override
    public synchronized void put(CommentEntity commentEntity) {
        if (commentEntity != null) {
            File commentEntitiyFile = this.buildFile(commentEntity.getCommentId());
            if (!isCached(commentEntity.getCommentId())) {
                String jsonString = this.serializer.serializeComment(commentEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, commentEntitiyFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(int commentId) {
        File commentEntitiyFile = this.buildFile(commentId);
        return this.fileManager.exists(commentEntitiyFile);
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

    private File buildFile(int commentId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(commentId);

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
