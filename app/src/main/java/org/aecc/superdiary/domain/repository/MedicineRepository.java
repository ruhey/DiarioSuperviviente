package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface MedicineRepository {

    void getMedicineList(MedicineListCallback MedicineListCallback);

    void getMedicineById(final int medicineId, MedicineDetailsCallback medicineCallback);

    void createMedicine(final Medicine medicine, MedicineCreationCallback medicineCallback);

    void saveMedicine(final Medicine medicine, MedicineSaveCallback medicineCallback);

    void deleteMedicine(final int medicineId, MedicineDetionCallback medicineDeletionCallback);

    interface MedicineListCallback {
        void onMedicineListLoaded(Collection<Medicine> medicinesCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface MedicineDetailsCallback {
        void onMedicineLoaded(Medicine medicine);

        void onError(ErrorBundle errorBundle);
    }

    interface MedicineCreationCallback {
        void onMedicineCreated(Medicine medicine);

        void onError(ErrorBundle errorBundle);
    }

    interface MedicineSaveCallback {
        void onMedicineSaved(Medicine medicine);

        void onError(ErrorBundle errorBundle);
    }

    interface MedicineDetionCallback {
        void onMedicineDeleted(Collection<Medicine> medicinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
