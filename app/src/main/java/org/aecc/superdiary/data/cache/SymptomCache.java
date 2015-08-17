package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.SymptomEntity;

public interface SymptomCache {

    void get(final int symptomId, final SymptomCacheCallback callback);

    void put(SymptomEntity symptomEntity);

    boolean isCached(final int symptomId);

    boolean isExpired();

    void evictAll();

    interface SymptomCacheCallback {
        void onSymptomEntityLoaded(SymptomEntity symptomEntity);

        void onError(Exception exception);
    }
}
