package org.aecc.superdiary.domain.interactor.comment;

import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface DeleteCommentUseCase extends Interactor {

    void execute(int commentId, Callback callback);


    interface Callback {
        void onCommentDataDeleted(Collection<Comment> commentsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
