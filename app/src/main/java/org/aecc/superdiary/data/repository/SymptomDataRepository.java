package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.SymptomEntity;
import org.aecc.superdiary.data.entity.mapper.SymptomEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateSymptomException;
import org.aecc.superdiary.data.exception.CantSaveSymptomException;
import org.aecc.superdiary.data.exception.SymptomNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.SymptomDataStore;
import org.aecc.superdiary.data.repository.datasource.SymptomDataStoreFactory;
import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SymptomDataRepository implements SymptomRepository {

    private final SymptomDataStoreFactory symptomDataStoreFactory;
    private final SymptomEntityDataMapper symptomEntityDataMapper;

    @Inject
    public SymptomDataRepository(SymptomDataStoreFactory symptomDataStoreFactory, SymptomEntityDataMapper symptomEntityDataMapper) {
        this.symptomDataStoreFactory = symptomDataStoreFactory;
        this.symptomEntityDataMapper = symptomEntityDataMapper;
    }

    @Override
    public void getSymptomList(final SymptomListCallback symptomListCallback) {
        final SymptomDataStore symptomDataStore = this.symptomDataStoreFactory.createDatabaseDataStore();
        symptomDataStore.getSymptomsEntityList(new SymptomDataStore.SymptomListCallback() {
            @Override
            public void onSymptomListLoaded(Collection<SymptomEntity> symptomsCollection) {
                Collection<Symptom> symptoms =
                        SymptomDataRepository.this.symptomEntityDataMapper.transform(symptomsCollection);
                symptomListCallback.onSymptomListLoaded(symptoms);
            }

            @Override
            public void onError(Exception exception) {
                symptomListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getSymptomById(final int symptomId, final SymptomDetailsCallback symptomCallback) {
        SymptomDataStore symptomDataStore = this.symptomDataStoreFactory.create(symptomId);
        symptomDataStore.getSymptomEntityDetails(symptomId, new SymptomDataStore.SymptomDetailsCallback() {
            @Override
            public void onSymptomEntityLoaded(SymptomEntity symptomEntity) {
                Symptom symptom = SymptomDataRepository.this.symptomEntityDataMapper.transform(symptomEntity);
                if (symptom != null) {
                    symptomCallback.onSymptomLoaded(symptom);
                } else {
                    symptomCallback.onError(new RepositoryErrorBundle(new SymptomNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                symptomCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createSymptom(final Symptom symptom, final SymptomCreationCallback symptomCreateCallback) {
        final SymptomDataStore symptomDataStore = this.symptomDataStoreFactory.createDatabaseDataStore();
        symptomDataStore.createSymptomEntity(symptom, new SymptomDataStore.SymptomCreationCallback() {

            @Override
            public void onSymptomCreated(SymptomEntity symptomEntity) {
                Symptom symptom = SymptomDataRepository.this.symptomEntityDataMapper.transform(symptomEntity);
                if (symptom != null) {
                    symptomCreateCallback.onSymptomCreated(symptom);
                } else {
                    symptomCreateCallback.onError(new RepositoryErrorBundle(new CantCreateSymptomException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                symptomCreateCallback.onError(new RepositoryErrorBundle(new CantCreateSymptomException()));
            }
        });
    }

    @Override
    public void saveSymptom(final Symptom symptom, final SymptomSaveCallback symptomSaveCallback) {
        final SymptomDataStore symptomDataStore = this.symptomDataStoreFactory.createDatabaseDataStore();
        symptomDataStore.saveSymptomEntity(symptom, new SymptomDataStore.SymptomSaveCallback() {

            @Override
            public void onSymptomSaved(SymptomEntity symptomEntity) {
                Symptom symptom = SymptomDataRepository.this.symptomEntityDataMapper.transform(symptomEntity);
                if (symptom != null) {
                    symptomSaveCallback.onSymptomSaved(symptom);
                } else {
                    symptomSaveCallback.onError(new RepositoryErrorBundle(new CantSaveSymptomException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                symptomSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteSymptom(final int symptomId, final SymptomDetionCallback symptomDeletionCallback) {
        final SymptomDataStore symptomDataStore = this.symptomDataStoreFactory.createDatabaseDataStore();
        symptomDataStore.deleteSymptomEntity(symptomId, new SymptomDataStore.SymptomDeletionCallback() {
            @Override
            public void onSymptomDeleted(Collection<SymptomEntity> symptomsCollection) {
                Collection<Symptom> symptoms =
                        SymptomDataRepository.this.symptomEntityDataMapper.transform(symptomsCollection);
                symptomDeletionCallback.onSymptomDeleted(symptoms);
            }

            @Override
            public void onError(Exception exception) {
                symptomDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
