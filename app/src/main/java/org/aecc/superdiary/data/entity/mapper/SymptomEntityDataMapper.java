package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.SymptomEntity;
import org.aecc.superdiary.domain.Symptom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SymptomEntityDataMapper {

    @Inject
    public SymptomEntityDataMapper() {
    }

    public Symptom transform(SymptomEntity symptomEntity) {
        Symptom symptom = null;

        if (symptomEntity != null) {
            symptom = new Symptom(symptomEntity.getSymptomId());
            symptom.setName(symptomEntity.getName());
            symptom.setDateSymptom(symptomEntity.getDateSymptom());
            symptom.setHourSymptom(symptomEntity.getHourSymptom());
            symptom.setDescription(symptomEntity.getDescription());
            //TODO:ver como hacer lo de la imagen
            symptom.setImage(symptomEntity.getImage());
        }
        return symptom;
    }

    public SymptomEntity untransform(Symptom symptom) {
        SymptomEntity symptomEntity = null;

        if (symptom != null) {
            symptomEntity.setSymptomId(symptom.getSymptomId());
            symptomEntity.setName(symptom.getName());
            symptomEntity.setHourSymptom(symptom.getHourSymptom());
            symptomEntity.setDateSymptom(symptom.getDateSymptom());
            symptomEntity.setDescription(symptom.getDescription());
            //TODO:ver como hacer lo de la imagen
            symptomEntity.setImage(symptom.getImage());
        }
        return symptomEntity;
    }

    public Collection<Symptom> transform(Collection<SymptomEntity> symptomEntityCollection) {
        List<Symptom> symptomList = new ArrayList<Symptom>();
        Symptom symptom;
        for (SymptomEntity symptomEntity : symptomEntityCollection) {
            symptom = transform(symptomEntity);
            if (symptom != null) {
                symptomList.add(symptom);
            }
        }
        return symptomList;
    }

    public Collection<SymptomEntity> untransform(Collection<Symptom> symptomCollection) {
        List<SymptomEntity> symptomEntityList = new ArrayList<SymptomEntity>();
        SymptomEntity symptomEntity;
        for (Symptom symptom : symptomCollection) {
            symptomEntity = untransform(symptom);
            if (symptomEntity != null) {
                symptomEntityList.add(symptomEntity);
            }
        }
        return symptomEntityList;
    }
}
