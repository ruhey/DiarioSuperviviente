package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.RoutineCache;
import org.aecc.superdiary.data.database.RoutineDatabaseAPI;
import org.aecc.superdiary.data.entity.RoutineEntity;
import org.aecc.superdiary.data.entity.mapper.RoutineEntityDataMapper;
import org.aecc.superdiary.domain.Routine;

import java.util.Collection;

public class DatabaseRoutineDataStore implements RoutineDataStore {
    private final RoutineCache routineCache;
    private final RoutineDatabaseAPI databaseAPI;
    private final RoutineEntityDataMapper routineEntityDataMapper;

    public DatabaseRoutineDataStore(RoutineCache routineCache, RoutineDatabaseAPI databaseAPI, RoutineEntityDataMapper routineEntityDataMapper) {
        this.routineCache = routineCache;
        this.databaseAPI = databaseAPI;
        this.routineEntityDataMapper = routineEntityDataMapper;

    }

    @Override
    public void getRoutinesEntityList(final RoutineListCallback routineListCallback) {
        this.databaseAPI.getRoutineEntityList(new RoutineDatabaseAPI.RoutineListCallback() {

            @Override
            public void onRoutineListLoaded(Collection<RoutineEntity> routinesCollection) {
                routineListCallback.onRoutineListLoaded(routinesCollection);
            }

            @Override
            public void onError(Exception exception) {
                routineListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getRoutineEntityDetails(int id, final RoutineDetailsCallback routineDetailsCallback) {
        this.databaseAPI.getRoutineEntityById(id, new RoutineDatabaseAPI.RoutineDetailsCallback() {

            @Override
            public void onRoutineEntityLoaded(RoutineEntity routineEntity) {
                routineDetailsCallback.onRoutineEntityLoaded(routineEntity);
                //CloudUserDataStore.this.putRoutineEntityInCache(routineEntity);
            }

            @Override
            public void onError(Exception exception) {
                routineDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createRoutineEntity(final Routine routine, final RoutineCreationCallback routineCreationCallback) {
        this.databaseAPI.createRoutineEntity(this.routineEntityDataMapper.untransform(routine), new RoutineDatabaseAPI.RoutineCreationCallback() {

            @Override
            public void onRoutineEntityCreated(RoutineEntity routineEntity) {
                routineCreationCallback.onRoutineCreated(routineEntity);
            }

            @Override
            public void onError(Exception exception) {
                routineCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveRoutineEntity(final Routine routine, final RoutineSaveCallback routineSaveCallback) {
        this.databaseAPI.saveRoutineEntity(this.routineEntityDataMapper.untransform(routine), new RoutineDatabaseAPI.RoutineSaveCallback() {
            @Override
            public void onRoutineEntitySaved(RoutineEntity routineEntity) {
                routineSaveCallback.onRoutineSaved(routineEntity);
            }

            @Override
            public void onError(Exception exception) {
                routineSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteRoutineEntity(final int id, final RoutineDeletionCallback routineDeletionCallback) {
        this.databaseAPI.deleteRoutineEntity(id, new RoutineDatabaseAPI.RoutineDeletionCallback() {
            @Override
            public void onRoutineEntityDeleted(Collection<RoutineEntity> routinesCollection) {
                routineDeletionCallback.onRoutineDeleted(routinesCollection);
            }

            @Override
            public void onError(Exception exception) {
                routineDeletionCallback.onError(exception);
            }
        });
    }


}
