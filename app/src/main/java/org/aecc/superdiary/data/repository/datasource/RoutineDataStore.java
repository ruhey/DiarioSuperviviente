package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.RoutineEntity;
import org.aecc.superdiary.domain.Routine;

import java.util.Collection;

public interface RoutineDataStore {

    void getRoutinesEntityList(RoutineListCallback routineListCallback);

    void getRoutineEntityDetails(int id, RoutineDetailsCallback routineDetailsCallback);

    void createRoutineEntity(Routine routine, RoutineCreationCallback routineCallback);

    void saveRoutineEntity(Routine routine, RoutineSaveCallback routineCallback);

    void deleteRoutineEntity(int id, RoutineDeletionCallback routineDeletionCallback);

    interface RoutineListCallback {
        void onRoutineListLoaded(Collection<RoutineEntity> routinesCollection);

        void onError(Exception exception);
    }

    interface RoutineDetailsCallback {
        void onRoutineEntityLoaded(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineCreationCallback {
        void onRoutineCreated(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineSaveCallback {
        void onRoutineSaved(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineDeletionCallback {
        void onRoutineDeleted(Collection<RoutineEntity> routinesCollection);

        void onError(Exception exception);
    }
}
