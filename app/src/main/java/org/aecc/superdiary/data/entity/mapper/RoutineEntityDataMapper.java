package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.RoutineEntity;
import org.aecc.superdiary.domain.Routine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoutineEntityDataMapper {

    @Inject
    public RoutineEntityDataMapper() {
    }

    public Routine transform(RoutineEntity routineEntity) {
        Routine routine = null;

        if (routineEntity != null) {
            routine = new Routine(routineEntity.getRoutineId());
            routine.setName(routineEntity.getName());
            routine.setPlace(routineEntity.getPlace());
            routine.setDescription(routineEntity.getDescription());
            routine.setDateRoutine(routineEntity.getDateRoutine());
            routine.setHourRoutine(routineEntity.getHourRoutine());
            routine.setDateAlarm(routineEntity.getDateAlarm());
            routine.setHourAlarm(routineEntity.getHourAlarm());
            routine.setDuration(routineEntity.getDuration());
            routine.setSatisfaction(routineEntity.getSatisfaction());
            //TODO:ver como hacer lo de la imagen
            routine.setImage(routineEntity.getImage());
        }
        return routine;
    }

    public RoutineEntity untransform(Routine routine) {
        RoutineEntity routineEntity = null;

        if (routine != null) {
            routineEntity.setRoutineId(routine.getRoutineId());
            routineEntity.setName(routine.getName());
            routineEntity.setPlace(routine.getPlace());
            routineEntity.setDescription(routine.getDescription());
            routineEntity.setDateRoutine(routine.getDateRoutine());
            routineEntity.setHourRoutine(routine.getHourRoutine());
            routineEntity.setDateAlarm(routine.getDateAlarm());
            routineEntity.setHourAlarm(routine.getHourAlarm());
            routineEntity.setDuration(routine.getDuration());
            routineEntity.setSatisfaction(routine.getSatisfaction());
            //TODO:ver como hacer lo de la imagen
            routineEntity.setImage(routine.getImage());
        }
        return routineEntity;
    }

    public Collection<Routine> transform(Collection<RoutineEntity> routineEntityCollection) {
        List<Routine> routineList = new ArrayList<Routine>();
        Routine routine;
        for (RoutineEntity routineEntity : routineEntityCollection) {
            routine = transform(routineEntity);
            if (routine != null) {
                routineList.add(routine);
            }
        }
        return routineList;
    }

    public Collection<RoutineEntity> untransform(Collection<Routine> routineCollection) {
        List<RoutineEntity> routineEntityList = new ArrayList<RoutineEntity>();
        RoutineEntity routineEntity;
        for (Routine routine : routineCollection) {
            routineEntity = untransform(routine);
            if (routineEntity != null) {
                routineEntityList.add(routineEntity);
            }
        }
        return routineEntityList;
    }
}
