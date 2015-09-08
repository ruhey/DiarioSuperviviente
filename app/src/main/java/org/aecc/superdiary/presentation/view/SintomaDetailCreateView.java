package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.RoutineModel;

public interface SintomaDetailCreateView extends LoadDataView {



    void createSymptom(int symtomId);

    void showMessage(String message);
}
