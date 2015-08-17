package org.aecc.superdiary.domain.interactor.exam;


import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteExamUseCase extends Interactor {
    void execute(int examId, Callback callback);


    interface Callback {
        void onExamDataDeleted(Collection<Exam> examsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
