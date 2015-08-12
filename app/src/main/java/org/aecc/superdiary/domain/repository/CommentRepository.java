package org.aecc.superdiary.domain.repository;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface CommentRepository {

    void getCommentList(CommentListCallback CommentListCallback);

    void getCommentById(final int commentId, CommentDetailsCallback commentCallback);

    void createComment(final Comment comment, CommentCreationCallback commentCallback);

    void saveComment(final Comment comment, CommentSaveCallback commentCallback);

    void deleteComment(final int commentId, CommentDetionCallback commentDeletionCallback);

    interface CommentListCallback {
        void onCommentListLoaded(Collection<Comment> commentsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface CommentDetailsCallback {
        void onCommentLoaded(Comment comment);

        void onError(ErrorBundle errorBundle);
    }

    interface CommentCreationCallback {
        void onCommentCreated(Comment comment);

        void onError(ErrorBundle errorBundle);
    }

    interface CommentSaveCallback {
        void onCommentSaved(Comment comment);

        void onError(ErrorBundle errorBundle);
    }

    interface CommentDetionCallback {
        void onCommentDeleted(Collection<Comment> commentsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
