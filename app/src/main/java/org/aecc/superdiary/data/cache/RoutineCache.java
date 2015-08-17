package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.RoutineEntity;

public interface RoutineCache {

    void get(final int routineId, final RoutineCacheCallback callback);

    void put(RoutineEntity routineEntity);

    boolean isCached(final int routineId);

    boolean isExpired();

    void evictAll();

    interface RoutineCacheCallback {
        void onRoutineEntityLoaded(RoutineEntity routineEntity);

        void onError(Exception exception);
    }
}
