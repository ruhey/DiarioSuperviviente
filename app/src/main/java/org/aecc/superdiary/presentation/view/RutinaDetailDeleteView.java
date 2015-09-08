package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.RoutineModel;

public interface RutinaDetailDeleteView extends LoadDataView{

    void deleteRoutine(int routineId);

    void showMessage(String message);

    void renderRoutine(RoutineModel routine);
}
