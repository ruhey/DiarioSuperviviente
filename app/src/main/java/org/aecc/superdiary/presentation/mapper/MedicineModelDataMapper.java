package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.MedicineModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class MedicineModelDataMapper {

    @Inject
    public MedicineModelDataMapper() {
    }

    public MedicineModel transform(Medicine medicine) {
        if (medicine == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        MedicineModel medicineModel = new MedicineModel(medicine.getMedicineId());
        medicineModel.setName(medicine.getName());
        medicineModel.setFirstDay(medicine.getFirstDay());
        medicineModel.setFirstHour(medicine.getFirstHour());
        medicineModel.setLastDay(medicine.getLastDay());
        medicineModel.setLastHour(medicine.getLastHour());
        medicineModel.setInterval(medicine.getInterval());
        medicineModel.setDescription(medicine.getDescription());
        medicineModel.setImage(medicine.getImage());

        return medicineModel;
    }

    public Collection<MedicineModel> transform(Collection<Medicine> medicinesCollection) {
        Collection<MedicineModel> medicineModelsCollection;

        if (medicinesCollection != null && !medicinesCollection.isEmpty()) {
            medicineModelsCollection = new ArrayList<MedicineModel>();
            for (Medicine medicine : medicinesCollection) {
                medicineModelsCollection.add(transform(medicine));
            }
        } else {
            medicineModelsCollection = Collections.emptyList();
        }

        return medicineModelsCollection;
    }
}
