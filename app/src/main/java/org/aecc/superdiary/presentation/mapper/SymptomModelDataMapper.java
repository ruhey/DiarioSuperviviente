package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.SymptomModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class SymptomModelDataMapper {

    @Inject
    public SymptomModelDataMapper() {
    }

    public SymptomModel transform(Symptom symptom) {
        if (symptom == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SymptomModel symptomModel = new SymptomModel(symptom.getSymptomId());
        symptomModel.setName(symptom.getName());
        symptomModel.setDateSymptom(symptom.getDateSymptom());
        symptomModel.setHourSymptom(symptom.getHourSymptom());
        symptomModel.setDescription(symptom.getDescription());
        symptomModel.setImage(symptom.getImage());

        return symptomModel;
    }

    public Collection<SymptomModel> transform(Collection<Symptom> symptomsCollection) {
        Collection<SymptomModel> symptomModelsCollection;

        if (symptomsCollection != null && !symptomsCollection.isEmpty()) {
            symptomModelsCollection = new ArrayList<SymptomModel>();
            for (Symptom symptom : symptomsCollection) {
                symptomModelsCollection.add(transform(symptom));
            }
        } else {
            symptomModelsCollection = Collections.emptyList();
        }

        return symptomModelsCollection;
    }
}
