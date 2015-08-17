package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetExamListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onExamListLoaded(Collection<Exam> examsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
