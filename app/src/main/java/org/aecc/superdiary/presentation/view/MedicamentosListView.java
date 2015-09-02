package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MedicineModel;

import java.util.Collection;

public interface MedicamentosListView extends LoadDataView {

    void renderMedicineList(Collection<MedicineModel> medicineModelCollection);

    void viewMedicine(MedicineModel medicineModel);
}
