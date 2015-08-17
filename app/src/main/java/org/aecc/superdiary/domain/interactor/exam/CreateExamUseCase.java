package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface CreateExamUseCase extends Interactor {
    void execute(Exam exam, Callback callback);


    interface Callback {
        void onExamDataCreated(Exam exam);

        void onError(ErrorBundle errorBundle);
    }
}