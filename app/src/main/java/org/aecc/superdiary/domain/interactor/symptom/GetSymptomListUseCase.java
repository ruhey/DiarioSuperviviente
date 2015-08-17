package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetSymptomListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onSymptomListLoaded(Collection<Symptom> symptomsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
