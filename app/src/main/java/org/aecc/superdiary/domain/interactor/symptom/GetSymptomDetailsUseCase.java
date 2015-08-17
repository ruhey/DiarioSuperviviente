package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetSymptomDetailsUseCase extends Interactor {
    void execute(int symptomId, Callback callback);


    interface Callback {
        void onSymptomDataLoaded(Symptom symptom);

        void onError(ErrorBundle errorBundle);
    }
}
