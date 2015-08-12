package org.aecc.superdiary.data.cache;


import org.aecc.superdiary.data.entity.CommentEntity;

public interface CommentCache {

    void get(final int commentId, final CommentCacheCallback callback);

    void put(CommentEntity commentEntity);

    boolean isCached(final int commentId);

    boolean isExpired();

    void evictAll();

    interface CommentCacheCallback {
        void onCommentEntityLoaded(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    /**
     * Created by fer on 12/08/2015.
     */
    class CommentCacheImpl {
    }
}
