package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetExamDetailsUseCase extends Interactor {
    void execute(int examId, Callback callback);


    interface Callback {
        void onExamDataLoaded(Exam exam);

        void onError(ErrorBundle errorBundle);
    }
}
