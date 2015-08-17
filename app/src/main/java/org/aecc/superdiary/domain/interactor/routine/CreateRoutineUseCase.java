package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface CreateRoutineUseCase extends Interactor {
    void execute(Routine routine, Callback callback);


    interface Callback {
        void onRoutineDataCreated(Routine routine);

        void onError(ErrorBundle errorBundle);
    }
}