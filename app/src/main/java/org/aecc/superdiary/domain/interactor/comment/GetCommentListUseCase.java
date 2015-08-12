package org.aecc.superdiary.domain.interactor.comment;

import org.aecc.superdiary.domain.Comment;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetCommentListUseCase extends Interactor {
    void execute(Callback callback);

    interface Callback {
        void onCommentListLoaded(Collection<Comment> commentsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
