package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.RoutineModel;

import java.util.Collection;

public interface RutinasListView extends LoadDataView {

    void renderRoutineList(Collection<RoutineModel> routineModelCollection);

    void viewRoutine(RoutineModel routineModel);
}
