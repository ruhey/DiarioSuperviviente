package org.aecc.superdiary.domain.interactor.routine;


import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteRoutineUseCase extends Interactor {
    void execute(int routineId, Callback callback);


    interface Callback {
        void onRoutineDataDeleted(Collection<Routine> routinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
