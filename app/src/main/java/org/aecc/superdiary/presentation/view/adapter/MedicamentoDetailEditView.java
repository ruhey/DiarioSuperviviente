package org.aecc.superdiary.presentation.view.adapter;

import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

import java.util.Locale;

public interface MedicamentoDetailEditView extends LoadDataView {

    void renderMedicine(MedicineModel medicine);

    void editMedicine(int medicineId);

    void showMessage(String message);
}
