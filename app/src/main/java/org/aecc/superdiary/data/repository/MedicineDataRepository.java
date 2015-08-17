package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.data.entity.mapper.MedicineEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateMedicineException;
import org.aecc.superdiary.data.exception.CantSaveMedicineException;
import org.aecc.superdiary.data.exception.MedicineNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.MedicineDataStore;
import org.aecc.superdiary.data.repository.datasource.MedicineDataStoreFactory;
import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.repository.MedicineRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MedicineDataRepository implements MedicineRepository {

    private final MedicineDataStoreFactory medicineDataStoreFactory;
    private final MedicineEntityDataMapper medicineEntityDataMapper;

    @Inject
    public MedicineDataRepository(MedicineDataStoreFactory medicineDataStoreFactory, MedicineEntityDataMapper medicineEntityDataMapper) {
        this.medicineDataStoreFactory = medicineDataStoreFactory;
        this.medicineEntityDataMapper = medicineEntityDataMapper;
    }

    @Override
    public void getMedicineList(final MedicineListCallback medicineListCallback) {
        final MedicineDataStore medicineDataStore = this.medicineDataStoreFactory.createDatabaseDataStore();
        medicineDataStore.getMedicinesEntityList(new MedicineDataStore.MedicineListCallback() {
            @Override
            public void onMedicineListLoaded(Collection<MedicineEntity> medicinesCollection) {
                Collection<Medicine> medicines =
                        MedicineDataRepository.this.medicineEntityDataMapper.transform(medicinesCollection);
                medicineListCallback.onMedicineListLoaded(medicines);
            }

            @Override
            public void onError(Exception exception) {
                medicineListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getMedicineById(final int medicineId, final MedicineDetailsCallback medicineCallback) {
        MedicineDataStore medicineDataStore = this.medicineDataStoreFactory.create(medicineId);
        medicineDataStore.getMedicineEntityDetails(medicineId, new MedicineDataStore.MedicineDetailsCallback() {
            @Override
            public void onMedicineEntityLoaded(MedicineEntity medicineEntity) {
                Medicine medicine = MedicineDataRepository.this.medicineEntityDataMapper.transform(medicineEntity);
                if (medicine != null) {
                    medicineCallback.onMedicineLoaded(medicine);
                } else {
                    medicineCallback.onError(new RepositoryErrorBundle(new MedicineNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                medicineCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createMedicine(final Medicine medicine, final MedicineCreationCallback medicineCreateCallback) {
        final MedicineDataStore medicineDataStore = this.medicineDataStoreFactory.createDatabaseDataStore();
        medicineDataStore.createMedicineEntity(medicine, new MedicineDataStore.MedicineCreationCallback() {

            @Override
            public void onMedicineCreated(MedicineEntity medicineEntity) {
                Medicine medicine = MedicineDataRepository.this.medicineEntityDataMapper.transform(medicineEntity);
                if (medicine != null) {
                    medicineCreateCallback.onMedicineCreated(medicine);
                } else {
                    medicineCreateCallback.onError(new RepositoryErrorBundle(new CantCreateMedicineException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                medicineCreateCallback.onError(new RepositoryErrorBundle(new CantCreateMedicineException()));
            }
        });
    }

    @Override
    public void saveMedicine(final Medicine medicine, final MedicineSaveCallback medicineSaveCallback) {
        final MedicineDataStore medicineDataStore = this.medicineDataStoreFactory.createDatabaseDataStore();
        medicineDataStore.saveMedicineEntity(medicine, new MedicineDataStore.MedicineSaveCallback() {

            @Override
            public void onMedicineSaved(MedicineEntity medicineEntity) {
                Medicine medicine = MedicineDataRepository.this.medicineEntityDataMapper.transform(medicineEntity);
                if (medicine != null) {
                    medicineSaveCallback.onMedicineSaved(medicine);
                } else {
                    medicineSaveCallback.onError(new RepositoryErrorBundle(new CantSaveMedicineException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                medicineSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteMedicine(final int medicineId, final MedicineDetionCallback medicineDeletionCallback) {
        final MedicineDataStore medicineDataStore = this.medicineDataStoreFactory.createDatabaseDataStore();
        medicineDataStore.deleteMedicineEntity(medicineId, new MedicineDataStore.MedicineDeletionCallback() {
            @Override
            public void onMedicineDeleted(Collection<MedicineEntity> medicinesCollection) {
                Collection<Medicine> medicines =
                        MedicineDataRepository.this.medicineEntityDataMapper.transform(medicinesCollection);
                medicineDeletionCallback.onMedicineDeleted(medicines);
            }

            @Override
            public void onError(Exception exception) {
                medicineDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
