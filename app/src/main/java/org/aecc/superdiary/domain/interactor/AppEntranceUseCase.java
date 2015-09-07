package org.aecc.superdiary.domain.interactor;


import org.aecc.superdiary.domain.AppEntranceElement;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface AppEntranceUseCase extends Interactor{

    void execute(Callback callback);

    interface Callback {

        void onDataLoaded(Collection<AppEntranceElement> appEntranceElementsList);
        void onError(ErrorBundle errorBundle);
    }
}
