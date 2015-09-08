package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.domain.Medicine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MedicineEntityDataMapper {

    @Inject
    public MedicineEntityDataMapper() {
    }

    public Medicine transform(MedicineEntity medicineEntity) {
        Medicine medicine = null;

        if (medicineEntity != null) {
            medicine = new Medicine(medicineEntity.getMedicineId());
            medicine.setName(medicineEntity.getName());
            medicine.setFirstDay(medicineEntity.getFirstDay());
            medicine.setFirstHour(medicineEntity.getFirstHour());
            medicine.setLastDay(medicineEntity.getLastDay());
            medicine.setLastHour(medicineEntity.getLastHour());
            medicine.setInterval(medicineEntity.getInterval());
            medicine.setDescription(medicineEntity.getDescription());
            //TODO:ver como hacer lo de la imagen
            medicine.setImage(medicineEntity.getImage());
        }
        return medicine;
    }

    public MedicineEntity untransform(Medicine medicine) {
        MedicineEntity medicineEntity = new MedicineEntity();

        if (medicine != null) {
            medicineEntity.setMedicineId(medicine.getMedicineId());
            medicineEntity.setName(medicine.getName());
            medicineEntity.setFirstDay(medicine.getFirstDay());
            medicineEntity.setFirstHour(medicine.getFirstHour());
            medicineEntity.setLastDay(medicine.getLastDay());
            medicineEntity.setLastHour(medicine.getLastHour());
            medicineEntity.setInterval(medicine.getInterval());
            medicineEntity.setDescription(medicine.getDescription());
            //TODO:ver como hacer lo de la imagen
            medicineEntity.setImage(medicine.getImage());
        }
        return medicineEntity;
    }

    public Collection<Medicine> transform(Collection<MedicineEntity> medicineEntityCollection) {
        List<Medicine> medicineList = new ArrayList<Medicine>();
        Medicine medicine;
        for (MedicineEntity medicineEntity : medicineEntityCollection) {
            medicine = transform(medicineEntity);
            if (medicine != null) {
                medicineList.add(medicine);
            }
        }
        return medicineList;
    }

    public Collection<MedicineEntity> untransform(Collection<Medicine> medicineCollection) {
        List<MedicineEntity> medicineEntityList = new ArrayList<MedicineEntity>();
        MedicineEntity medicineEntity;
        for (Medicine medicine : medicineCollection) {
            medicineEntity = untransform(medicine);
            if (medicineEntity != null) {
                medicineEntityList.add(medicineEntity);
            }
        }
        return medicineEntityList;
    }
}
