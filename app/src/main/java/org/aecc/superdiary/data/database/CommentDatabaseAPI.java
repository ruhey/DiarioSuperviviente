package org.aecc.superdiary.data.database;


import org.aecc.superdiary.data.entity.CommentEntity;

import java.util.Collection;

public interface CommentDatabaseAPI {

    void getCommentEntityList(final CommentListCallback commentListCallback);

    void getCommentEntityById(final int commentId, final CommentDetailsCallback commentDetailsCallback);

    void createCommentEntity(final CommentEntity comment, CommentCreationCallback commentCreationCallback);

    void saveCommentEntity(final CommentEntity comment, CommentSaveCallback commentSaveCallback);

    void deleteCommentEntity(final int commentId, CommentDeletionCallback commentDeletionCallback);

    interface CommentListCallback {
        void onCommentListLoaded(Collection<CommentEntity> commentsCollection);

        void onError(Exception exception);
    }

    interface CommentDetailsCallback {
        void onCommentEntityLoaded(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentCreationCallback {
        void onCommentEntityCreated(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentSaveCallback {
        void onCommentEntitySaved(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentDeletionCallback {
        void onCommentEntityDeleted(Collection<CommentEntity> commentsCollection);

        void onError(Exception exception);
    }
}
