package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.MedicineCache;
import org.aecc.superdiary.data.database.MedicineDatabaseAPI;
import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.data.entity.mapper.MedicineEntityDataMapper;
import org.aecc.superdiary.domain.Medicine;

import java.util.Collection;

public class DatabaseMedicineDataStore implements MedicineDataStore {
    private final MedicineCache medicineCache;
    private final MedicineDatabaseAPI databaseAPI;
    private final MedicineEntityDataMapper medicineEntityDataMapper;

    public DatabaseMedicineDataStore(MedicineCache medicineCache, MedicineDatabaseAPI databaseAPI, MedicineEntityDataMapper medicineEntityDataMapper) {
        this.medicineCache = medicineCache;
        this.databaseAPI = databaseAPI;
        this.medicineEntityDataMapper = medicineEntityDataMapper;

    }

    @Override
    public void getMedicinesEntityList(final MedicineListCallback medicineListCallback) {
        this.databaseAPI.getMedicineEntityList(new MedicineDatabaseAPI.MedicineListCallback() {

            @Override
            public void onMedicineListLoaded(Collection<MedicineEntity> medicinesCollection) {
                medicineListCallback.onMedicineListLoaded(medicinesCollection);
            }

            @Override
            public void onError(Exception exception) {
                medicineListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getMedicineEntityDetails(int id, final MedicineDetailsCallback medicineDetailsCallback) {
        this.databaseAPI.getMedicineEntityById(id, new MedicineDatabaseAPI.MedicineDetailsCallback() {

            @Override
            public void onMedicineEntityLoaded(MedicineEntity medicineEntity) {
                medicineDetailsCallback.onMedicineEntityLoaded(medicineEntity);
                //CloudUserDataStore.this.putMedicineEntityInCache(medicineEntity);
            }

            @Override
            public void onError(Exception exception) {
                medicineDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createMedicineEntity(final Medicine medicine, final MedicineCreationCallback medicineCreationCallback) {
        this.databaseAPI.createMedicineEntity(this.medicineEntityDataMapper.untransform(medicine), new MedicineDatabaseAPI.MedicineCreationCallback() {

            @Override
            public void onMedicineEntityCreated(MedicineEntity medicineEntity) {
                medicineCreationCallback.onMedicineCreated(medicineEntity);
            }

            @Override
            public void onError(Exception exception) {
                medicineCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveMedicineEntity(final Medicine medicine, final MedicineSaveCallback medicineSaveCallback) {
        this.databaseAPI.saveMedicineEntity(this.medicineEntityDataMapper.untransform(medicine), new MedicineDatabaseAPI.MedicineSaveCallback() {
            @Override
            public void onMedicineEntitySaved(MedicineEntity medicineEntity) {
                medicineSaveCallback.onMedicineSaved(medicineEntity);
            }

            @Override
            public void onError(Exception exception) {
                medicineSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteMedicineEntity(final int id, final MedicineDeletionCallback medicineDeletionCallback) {
        this.databaseAPI.deleteMedicineEntity(id, new MedicineDatabaseAPI.MedicineDeletionCallback() {
            @Override
            public void onMedicineEntityDeleted(Collection<MedicineEntity> medicinesCollection) {
                medicineDeletionCallback.onMedicineDeleted(medicinesCollection);
            }

            @Override
            public void onError(Exception exception) {
                medicineDeletionCallback.onError(exception);
            }
        });
    }


}
