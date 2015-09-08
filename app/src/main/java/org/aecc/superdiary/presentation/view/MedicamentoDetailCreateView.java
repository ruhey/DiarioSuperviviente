package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MedicineModel;

public interface MedicamentoDetailCreateView extends LoadDataView {



    void createMedicine(int medicineId);

    void showMessage(String message);
}
