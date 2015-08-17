package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface SymptomRepository {

    void getSymptomList(SymptomListCallback SymptomListCallback);

    void getSymptomById(final int symptomId, SymptomDetailsCallback symptomCallback);

    void createSymptom(final Symptom symptom, SymptomCreationCallback symptomCallback);

    void saveSymptom(final Symptom symptom, SymptomSaveCallback symptomCallback);

    void deleteSymptom(final int symptomId, SymptomDetionCallback symptomDeletionCallback);

    interface SymptomListCallback {
        void onSymptomListLoaded(Collection<Symptom> symptomsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface SymptomDetailsCallback {
        void onSymptomLoaded(Symptom symptom);

        void onError(ErrorBundle errorBundle);
    }

    interface SymptomCreationCallback {
        void onSymptomCreated(Symptom symptom);

        void onError(ErrorBundle errorBundle);
    }

    interface SymptomSaveCallback {
        void onSymptomSaved(Symptom symptom);

        void onError(ErrorBundle errorBundle);
    }

    interface SymptomDetionCallback {
        void onSymptomDeleted(Collection<Symptom> symptomsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
