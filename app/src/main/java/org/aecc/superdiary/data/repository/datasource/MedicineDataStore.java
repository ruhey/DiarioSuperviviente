package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.domain.Medicine;

import java.util.Collection;

public interface MedicineDataStore {

    void getMedicinesEntityList(MedicineListCallback medicineListCallback);

    void getMedicineEntityDetails(int id, MedicineDetailsCallback medicineDetailsCallback);

    void createMedicineEntity(Medicine medicine, MedicineCreationCallback medicineCallback);

    void saveMedicineEntity(Medicine medicine, MedicineSaveCallback medicineCallback);

    void deleteMedicineEntity(int id, MedicineDeletionCallback medicineDeletionCallback);

    interface MedicineListCallback {
        void onMedicineListLoaded(Collection<MedicineEntity> medicinesCollection);

        void onError(Exception exception);
    }

    interface MedicineDetailsCallback {
        void onMedicineEntityLoaded(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineCreationCallback {
        void onMedicineCreated(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineSaveCallback {
        void onMedicineSaved(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }

    interface MedicineDeletionCallback {
        void onMedicineDeleted(Collection<MedicineEntity> medicinesCollection);

        void onError(Exception exception);
    }
}
