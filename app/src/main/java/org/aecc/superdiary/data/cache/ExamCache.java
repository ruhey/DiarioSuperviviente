package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.ExamEntity;

public interface ExamCache {

    void get(final int examId, final ExamCacheCallback callback);

    void put(ExamEntity examEntity);

    boolean isCached(final int examId);

    boolean isExpired();

    void evictAll();

    interface ExamCacheCallback {
        void onExamEntityLoaded(ExamEntity examEntity);

        void onError(Exception exception);
    }
}
