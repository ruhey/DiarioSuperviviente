package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.RoutineEntity;

import java.util.Collection;

public interface RoutineDatabaseAPI {

    void getRoutineEntityList(final RoutineListCallback routineListCallback);

    void getRoutineEntityById(final int routineId, final RoutineDetailsCallback routineDetailsCallback);

    void createRoutineEntity(final RoutineEntity routine, final RoutineCreationCallback routineCreationCallback);

    void saveRoutineEntity(final RoutineEntity routine, final RoutineSaveCallback routineSaveCallback);

    void deleteRoutineEntity(final int routineId, final RoutineDeletionCallback routineDeletionCallback);

    interface RoutineListCallback {
        void onRoutineListLoaded(Collection<RoutineEntity> routinesCollection);

        void onError(Exception exception);
    }

    interface RoutineDetailsCallback {
        void onRoutineEntityLoaded(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineCreationCallback {
        void onRoutineEntityCreated(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineSaveCallback {
        void onRoutineEntitySaved(RoutineEntity routineEntity);

        void onError(Exception exception);
    }

    interface RoutineDeletionCallback {
        void onRoutineEntityDeleted(Collection<RoutineEntity> routinesCollection);

        void onError(Exception exception);
    }
}
