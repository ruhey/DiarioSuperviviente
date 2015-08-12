package org.aecc.superdiary.data.repository.datasource;


import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.domain.Comment;

import java.util.Collection;

public interface CommentDataStore {

    void getCommentsEntityList(CommentListCallback commentListCallback);

    void getCommentEntityDetails(int id, CommentDetailsCallback commentDetailsCallback);

    void createCommentEntity(Comment comment, CommentCreationCallback commentCallback);

    void saveCommentEntity(Comment comment, CommentSaveCallback commentCallback);

    void deleteCommentEntity(int id, CommentDetionCallback commentDeletionCallback);

    interface CommentListCallback {
        void onCommentListLoaded(Collection<CommentEntity> commentsCollection);

        void onError(Exception exception);
    }

    interface CommentDetailsCallback {
        void onCommentEntityLoaded(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentCreationCallback {
        void onCommentCreated(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentSaveCallback {
        void onCommentSaved(CommentEntity commentEntity);

        void onError(Exception exception);
    }

    interface CommentDetionCallback {
        void onCommentDeleted(Collection<CommentEntity> commentsCollection);

        void onError(Exception exception);
    }
}
