package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MedicineModel;

public interface MedicamentoDetailDeleteView extends LoadDataView{

    void deleteMedicine(int medicineId);

    void showMessage(String message);

    void renderMedicine(MedicineModel medicine);
}
