package org.aecc.superdiary.domain.interactor.comment;


import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

public interface CreateCommentUseCase extends Interactor {

    public void execute(Comment comment, Callback callback);


    interface Callback {
        void onCommentDataCreated(Comment comment);

        void onError(ErrorBundle errorBundle);
    }
}
