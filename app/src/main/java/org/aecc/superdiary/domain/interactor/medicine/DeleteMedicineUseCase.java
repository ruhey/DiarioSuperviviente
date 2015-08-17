package org.aecc.superdiary.domain.interactor.medicine;


import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteMedicineUseCase extends Interactor {
    void execute(int medicineId, Callback callback);


    interface Callback {
        void onMedicineDataDeleted(Collection<Medicine> medicinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
