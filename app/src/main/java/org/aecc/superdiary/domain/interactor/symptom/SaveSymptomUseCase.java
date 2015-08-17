package org.aecc.superdiary.domain.interactor.symptom;


import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

public interface SaveSymptomUseCase extends Interactor {

    void execute(Symptom symptom, Callback callback);


    interface Callback {
        void onSymptomDataSaved(Symptom symptom);

        void onError(ErrorBundle errorBundle);
    }

}
