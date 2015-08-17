package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetRoutineDetailsUseCase extends Interactor {
    void execute(int routineId, Callback callback);


    interface Callback {
        void onRoutineDataLoaded(Routine routine);

        void onError(ErrorBundle errorBundle);
    }
}
