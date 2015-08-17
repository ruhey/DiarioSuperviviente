package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.SymptomCache;
import org.aecc.superdiary.data.database.SymptomDatabaseAPI;
import org.aecc.superdiary.data.database.SymptomDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.SymptomEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SymptomDataStoreFactory {

    private final Context context;
    private final SymptomCache symptomCache;
    private final SymptomEntityDataMapper symptomEntityDataMapper;

    @Inject
    public SymptomDataStoreFactory(Context context, SymptomCache symptomCache, SymptomEntityDataMapper symptomEntityDataMapper) {
        this.context = context;
        this.symptomCache = symptomCache;
        this.symptomEntityDataMapper = symptomEntityDataMapper;
    }

    public SymptomDataStore create(int symptomId) {
        SymptomDataStore symptomDataStore;

        if (!this.symptomCache.isExpired() && this.symptomCache.isCached(symptomId)) {
            //TODO: vamos a suar Cache?
            //symptomDataStore = new DiskSymptomDataStore(this.symptomCache);
            symptomDataStore = createDatabaseDataStore();
        } else {
            symptomDataStore = createDatabaseDataStore();
        }

        return symptomDataStore;
    }

    public SymptomDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        SymptomDatabaseAPI dataBaseAPI = new SymptomDatabaseAPIImpl(this.context);
        return new DatabaseSymptomDataStore(this.symptomCache, dataBaseAPI, symptomEntityDataMapper);
    }
}
