package org.aecc.superdiary.presentation.view;

import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

public interface MedicamentoDetailEditView extends LoadDataView {

    void renderMedicine(MedicineModel medicine);

    void editMedicine(int medicineId);

    void showMessage(String message);
}