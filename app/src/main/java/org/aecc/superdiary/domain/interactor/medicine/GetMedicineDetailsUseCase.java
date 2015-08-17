package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetMedicineDetailsUseCase extends Interactor {
    void execute(int medicineId, Callback callback);


    interface Callback {
        void onMedicineDataLoaded(Medicine medicine);

        void onError(ErrorBundle errorBundle);
    }
}
