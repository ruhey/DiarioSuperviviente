package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetRoutineListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onRoutineListLoaded(Collection<Routine> routinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
