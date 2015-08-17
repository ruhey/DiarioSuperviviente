package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface RoutineRepository {

    void getRoutineList(RoutineListCallback RoutineListCallback);

    void getRoutineById(final int routineId, RoutineDetailsCallback routineCallback);

    void createRoutine(final Routine routine, RoutineCreationCallback routineCallback);

    void saveRoutine(final Routine routine, RoutineSaveCallback routineCallback);

    void deleteRoutine(final int routineId, RoutineDetionCallback routineDeletionCallback);

    interface RoutineListCallback {
        void onRoutineListLoaded(Collection<Routine> routinesCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface RoutineDetailsCallback {
        void onRoutineLoaded(Routine routine);

        void onError(ErrorBundle errorBundle);
    }

    interface RoutineCreationCallback {
        void onRoutineCreated(Routine routine);

        void onError(ErrorBundle errorBundle);
    }

    interface RoutineSaveCallback {
        void onRoutineSaved(Routine routine);

        void onError(ErrorBundle errorBundle);
    }

    interface RoutineDetionCallback {
        void onRoutineDeleted(Collection<Routine> routinesCollection);

        void onError(ErrorBundle errorBundle);
    }
}
