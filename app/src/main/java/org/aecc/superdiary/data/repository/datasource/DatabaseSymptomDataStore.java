package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.SymptomCache;
import org.aecc.superdiary.data.database.SymptomDatabaseAPI;
import org.aecc.superdiary.data.entity.SymptomEntity;
import org.aecc.superdiary.data.entity.mapper.SymptomEntityDataMapper;
import org.aecc.superdiary.domain.Symptom;

import java.util.Collection;

public class DatabaseSymptomDataStore implements SymptomDataStore {
    private final SymptomCache symptomCache;
    private final SymptomDatabaseAPI databaseAPI;
    private final SymptomEntityDataMapper symptomEntityDataMapper;

    public DatabaseSymptomDataStore(SymptomCache symptomCache, SymptomDatabaseAPI databaseAPI, SymptomEntityDataMapper symptomEntityDataMapper) {
        this.symptomCache = symptomCache;
        this.databaseAPI = databaseAPI;
        this.symptomEntityDataMapper = symptomEntityDataMapper;

    }

    @Override
    public void getSymptomsEntityList(final SymptomListCallback symptomListCallback) {
        this.databaseAPI.getSymptomEntityList(new SymptomDatabaseAPI.SymptomListCallback() {

            @Override
            public void onSymptomListLoaded(Collection<SymptomEntity> symptomsCollection) {
                symptomListCallback.onSymptomListLoaded(symptomsCollection);
            }

            @Override
            public void onError(Exception exception) {
                symptomListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getSymptomEntityDetails(int id, final SymptomDetailsCallback symptomDetailsCallback) {
        this.databaseAPI.getSymptomEntityById(id, new SymptomDatabaseAPI.SymptomDetailsCallback() {

            @Override
            public void onSymptomEntityLoaded(SymptomEntity symptomEntity) {
                symptomDetailsCallback.onSymptomEntityLoaded(symptomEntity);
                //CloudUserDataStore.this.putSymptomEntityInCache(symptomEntity);
            }

            @Override
            public void onError(Exception exception) {
                symptomDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createSymptomEntity(final Symptom symptom, final SymptomCreationCallback symptomCreationCallback) {
        this.databaseAPI.createSymptomEntity(this.symptomEntityDataMapper.untransform(symptom), new SymptomDatabaseAPI.SymptomCreationCallback() {

            @Override
            public void onSymptomEntityCreated(SymptomEntity symptomEntity) {
                symptomCreationCallback.onSymptomCreated(symptomEntity);
            }

            @Override
            public void onError(Exception exception) {
                symptomCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveSymptomEntity(final Symptom symptom, final SymptomSaveCallback symptomSaveCallback) {
        this.databaseAPI.saveSymptomEntity(this.symptomEntityDataMapper.untransform(symptom), new SymptomDatabaseAPI.SymptomSaveCallback() {
            @Override
            public void onSymptomEntitySaved(SymptomEntity symptomEntity) {
                symptomSaveCallback.onSymptomSaved(symptomEntity);
            }

            @Override
            public void onError(Exception exception) {
                symptomSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteSymptomEntity(final int id, final SymptomDeletionCallback symptomDeletionCallback) {
        this.databaseAPI.deleteSymptomEntity(id, new SymptomDatabaseAPI.SymptomDeletionCallback() {
            @Override
            public void onSymptomEntityDeleted(Collection<SymptomEntity> symptomsCollection) {
                symptomDeletionCallback.onSymptomDeleted(symptomsCollection);
            }

            @Override
            public void onError(Exception exception) {
                symptomDeletionCallback.onError(exception);
            }
        });
    }


}
