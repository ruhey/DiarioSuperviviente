package org.aecc.superdiary.domain.interactor.symptom;


import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteSymptomUseCase extends Interactor {
    void execute(int symptomId, Callback callback);


    interface Callback {
        void onSymptomDataDeleted(Collection<Symptom> symptomsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
