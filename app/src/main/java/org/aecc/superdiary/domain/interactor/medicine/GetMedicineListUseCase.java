package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetMedicineListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onMedicineListLoaded(Collection<Medicine> medicinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
