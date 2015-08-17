package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.SymptomEntity;

import java.util.Collection;

public interface SymptomDatabaseAPI {

    void getSymptomEntityList(final SymptomListCallback symptomListCallback);

    void getSymptomEntityById(final int symptomId, final SymptomDetailsCallback symptomDetailsCallback);

    void createSymptomEntity(final SymptomEntity symptom, final SymptomCreationCallback symptomCreationCallback);

    void saveSymptomEntity(final SymptomEntity symptom, final SymptomSaveCallback symptomSaveCallback);

    void deleteSymptomEntity(final int symptomId, final SymptomDeletionCallback symptomDeletionCallback);

    interface SymptomListCallback {
        void onSymptomListLoaded(Collection<SymptomEntity> symptomsCollection);

        void onError(Exception exception);
    }

    interface SymptomDetailsCallback {
        void onSymptomEntityLoaded(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomCreationCallback {
        void onSymptomEntityCreated(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomSaveCallback {
        void onSymptomEntitySaved(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }

    interface SymptomDeletionCallback {
        void onSymptomEntityDeleted(Collection<SymptomEntity> symptomsCollection);

        void onError(Exception exception);
    }
}
