package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.SymptomEntity;
import org.aecc.superdiary.domain.Symptom;

import java.util.Collection;

public interface SymptomDataStore {

    void getSymptomsEntityList(SymptomListCallback symptomListCallback);

    void getSymptomEntityDetails(int id, SymptomDetailsCallback symptomDetailsCallback);

    void createSymptomEntity(Symptom symptom, SymptomCreationCallback symptomCallback);

    void saveSymptomEntity(Symptom symptom, SymptomSaveCallback symptomCallback);

    void deleteSymptomEntity(int id, SymptomDeletionCallback symptomDeletionCallback);

    interface SymptomListCallback {
        void onSymptomListLoaded(Collection<SymptomEntity> symptomsCollection);

        void onError(Exception exception);
    }

    interface SymptomDetailsCallback {
        void onSymptomEntityLoaded(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomCreationCallback {
        void onSymptomCreated(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomSaveCallback {
        void onSymptomSaved(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomDeletionCallback {
        void onSymptomDeleted(Collection<SymptomEntity> symptomsCollection);

        void onError(Exception exception);
    }
}
