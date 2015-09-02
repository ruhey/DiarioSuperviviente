package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.RoutineModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class RoutineModelDataMapper {

    @Inject
    public RoutineModelDataMapper() {
    }

    public RoutineModel transform(Routine routine) {
        if (routine == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        RoutineModel routineModel = new RoutineModel(routine.getRoutineId());
        routineModel.setName(routine.getName());
        routineModel.setPlace(routine.getPlace());
        routineModel.setDescription(routine.getDescription());
        routineModel.setDateRoutine(routine.getDateRoutine());
        routineModel.setHourRoutine(routine.getHourRoutine());
        routineModel.setDateAlarm(routine.getDateAlarm());
        routineModel.setHourAlarm(routine.getHourAlarm());
        routineModel.setDuration(routine.getDuration());
        routineModel.setSatisfaction(routine.getSatisfaction());
        routineModel.setImage(routine.getImage());

        return routineModel;
    }

    public Collection<RoutineModel> transform(Collection<Routine> routinesCollection) {
        Collection<RoutineModel> routineModelsCollection;

        if (routinesCollection != null && !routinesCollection.isEmpty()) {
            routineModelsCollection = new ArrayList<RoutineModel>();
            for (Routine routine : routinesCollection) {
                routineModelsCollection.add(transform(routine));
            }
        } else {
            routineModelsCollection = Collections.emptyList();
        }

        return routineModelsCollection;
    }
}
