package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.RoutineCache;
import org.aecc.superdiary.data.database.RoutineDatabaseAPI;
import org.aecc.superdiary.data.database.RoutineDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.RoutineEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoutineDataStoreFactory {

    private final Context context;
    private final RoutineCache routineCache;
    private final RoutineEntityDataMapper routineEntityDataMapper;

    @Inject
    public RoutineDataStoreFactory(Context context, RoutineCache routineCache, RoutineEntityDataMapper routineEntityDataMapper) {
        this.context = context;
        this.routineCache = routineCache;
        this.routineEntityDataMapper = routineEntityDataMapper;
    }

    public RoutineDataStore create(int routineId) {
        RoutineDataStore routineDataStore;

        if (!this.routineCache.isExpired() && this.routineCache.isCached(routineId)) {
            //TODO: vamos a suar Cache?
            //routineDataStore = new DiskRoutineDataStore(this.routineCache);
            routineDataStore = createDatabaseDataStore();
        } else {
            routineDataStore = createDatabaseDataStore();
        }

        return routineDataStore;
    }

    public RoutineDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        RoutineDatabaseAPI dataBaseAPI = new RoutineDatabaseAPIImpl(this.context);
        return new DatabaseRoutineDataStore(this.routineCache, dataBaseAPI, routineEntityDataMapper);
    }
}
