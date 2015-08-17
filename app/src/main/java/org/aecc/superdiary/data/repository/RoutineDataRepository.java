package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.RoutineEntity;
import org.aecc.superdiary.data.entity.mapper.RoutineEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateRoutineException;
import org.aecc.superdiary.data.exception.CantSaveRoutineException;
import org.aecc.superdiary.data.exception.RoutineNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.RoutineDataStore;
import org.aecc.superdiary.data.repository.datasource.RoutineDataStoreFactory;
import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.repository.RoutineRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoutineDataRepository implements RoutineRepository {

    private final RoutineDataStoreFactory routineDataStoreFactory;
    private final RoutineEntityDataMapper routineEntityDataMapper;

    @Inject
    public RoutineDataRepository(RoutineDataStoreFactory routineDataStoreFactory, RoutineEntityDataMapper routineEntityDataMapper) {
        this.routineDataStoreFactory = routineDataStoreFactory;
        this.routineEntityDataMapper = routineEntityDataMapper;
    }

    @Override
    public void getRoutineList(final RoutineListCallback routineListCallback) {
        final RoutineDataStore routineDataStore = this.routineDataStoreFactory.createDatabaseDataStore();
        routineDataStore.getRoutinesEntityList(new RoutineDataStore.RoutineListCallback() {
            @Override
            public void onRoutineListLoaded(Collection<RoutineEntity> routinesCollection) {
                Collection<Routine> routines =
                        RoutineDataRepository.this.routineEntityDataMapper.transform(routinesCollection);
                routineListCallback.onRoutineListLoaded(routines);
            }

            @Override
            public void onError(Exception exception) {
                routineListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getRoutineById(final int routineId, final RoutineDetailsCallback routineCallback) {
        RoutineDataStore routineDataStore = this.routineDataStoreFactory.create(routineId);
        routineDataStore.getRoutineEntityDetails(routineId, new RoutineDataStore.RoutineDetailsCallback() {
            @Override
            public void onRoutineEntityLoaded(RoutineEntity routineEntity) {
                Routine routine = RoutineDataRepository.this.routineEntityDataMapper.transform(routineEntity);
                if (routine != null) {
                    routineCallback.onRoutineLoaded(routine);
                } else {
                    routineCallback.onError(new RepositoryErrorBundle(new RoutineNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                routineCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createRoutine(final Routine routine, final RoutineCreationCallback routineCreateCallback) {
        final RoutineDataStore routineDataStore = this.routineDataStoreFactory.createDatabaseDataStore();
        routineDataStore.createRoutineEntity(routine, new RoutineDataStore.RoutineCreationCallback() {

            @Override
            public void onRoutineCreated(RoutineEntity routineEntity) {
                Routine routine = RoutineDataRepository.this.routineEntityDataMapper.transform(routineEntity);
                if (routine != null) {
                    routineCreateCallback.onRoutineCreated(routine);
                } else {
                    routineCreateCallback.onError(new RepositoryErrorBundle(new CantCreateRoutineException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                routineCreateCallback.onError(new RepositoryErrorBundle(new CantCreateRoutineException()));
            }
        });
    }

    @Override
    public void saveRoutine(final Routine routine, final RoutineSaveCallback routineSaveCallback) {
        final RoutineDataStore routineDataStore = this.routineDataStoreFactory.createDatabaseDataStore();
        routineDataStore.saveRoutineEntity(routine, new RoutineDataStore.RoutineSaveCallback() {

            @Override
            public void onRoutineSaved(RoutineEntity routineEntity) {
                Routine routine = RoutineDataRepository.this.routineEntityDataMapper.transform(routineEntity);
                if (routine != null) {
                    routineSaveCallback.onRoutineSaved(routine);
                } else {
                    routineSaveCallback.onError(new RepositoryErrorBundle(new CantSaveRoutineException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                routineSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteRoutine(final int routineId, final RoutineDetionCallback routineDeletionCallback) {
        final RoutineDataStore routineDataStore = this.routineDataStoreFactory.createDatabaseDataStore();
        routineDataStore.deleteRoutineEntity(routineId, new RoutineDataStore.RoutineDeletionCallback() {
            @Override
            public void onRoutineDeleted(Collection<RoutineEntity> routinesCollection) {
                Collection<Routine> routines =
                        RoutineDataRepository.this.routineEntityDataMapper.transform(routinesCollection);
                routineDeletionCallback.onRoutineDeleted(routines);
            }

            @Override
            public void onError(Exception exception) {
                routineDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
