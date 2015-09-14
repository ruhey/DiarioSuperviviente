package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.RoutineModel;

public interface RutinaDetailCreateView extends LoadDataView {

    void goToList();

    void createRoutine(int routineId);

    void showMessage(String message);
}
