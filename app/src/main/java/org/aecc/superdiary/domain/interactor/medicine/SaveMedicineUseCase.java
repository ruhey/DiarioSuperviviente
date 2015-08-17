package org.aecc.superdiary.domain.interactor.medicine;


import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

public interface SaveMedicineUseCase extends Interactor {

    void execute(Medicine medicine, Callback callback);


    interface Callback {
        void onMedicineDataSaved(Medicine medicine);

        void onError(ErrorBundle errorBundle);
    }

}
