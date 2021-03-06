package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.RoutineModel;

public interface RutinaDetailView extends LoadDataView {

    void renderRoutine(RoutineModel routine);

    void editRoutine(int routineId);

    void deleteRoutine(int routineId);
}
