package org.aecc.superdiary.domain.interactor.comment;

import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetCommentDetailsUseCase extends Interactor {

    void execute(int commentId, Callback callback);

    interface Callback {
        void onCommentDataLoaded(Comment comment);

        void onError(ErrorBundle errorBundle);
    }
}
