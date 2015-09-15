package org.aecc.superdiary.presentation.view;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

public interface RutinaDetailEditView extends LoadDataView {

    void renderRoutine(RoutineModel routine);

    void editRoutine(int routineId);

    void showMessage(String message);

    void goBack();
}