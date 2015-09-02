package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MedicineModel;

public interface MedicamentoDetailView extends LoadDataView {

    void renderMedicine(MedicineModel medicine);
}
