package org.aecc.superdiary.presentation.view.adapter;

import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

import java.util.Locale;

public interface RutinaDetailEditView extends LoadDataView {

    void renderRoutine(RoutineModel routine);

    void editRoutine(int routineId);

    void showMessage(String message);
}
