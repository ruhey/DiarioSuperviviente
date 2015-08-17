package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.MedicineEntity;

import java.util.Collection;

public interface MedicineDatabaseAPI {

    void getMedicineEntityList(final MedicineListCallback medicineListCallback);

    void getMedicineEntityById(final int medicineId, final MedicineDetailsCallback medicineDetailsCallback);

    void createMedicineEntity(final MedicineEntity medicine, final MedicineCreationCallback medicineCreationCallback);

    void saveMedicineEntity(final MedicineEntity medicine, final MedicineSaveCallback medicineSaveCallback);

    void deleteMedicineEntity(final int medicineId, final MedicineDeletionCallback medicineDeletionCallback);

    interface MedicineListCallback {
        void onMedicineListLoaded(Collection<MedicineEntity> medicinesCollection);

        void onError(Exception exception);
    }

    interface MedicineDetailsCallback {
        void onMedicineEntityLoaded(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineCreationCallback {
        void onMedicineEntityCreated(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineSaveCallback {
        void onMedicineEntitySaved(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineDeletionCallback {
        void onMedicineEntityDeleted(Collection<MedicineEntity> medicinesCollection);

        void onError(Exception exception);
    }
}
