package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.MedicineCache;
import org.aecc.superdiary.data.database.MedicineDatabaseAPI;
import org.aecc.superdiary.data.database.MedicineDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.MedicineEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MedicineDataStoreFactory {

    private final Context context;
    private final MedicineCache medicineCache;
    private final MedicineEntityDataMapper medicineEntityDataMapper;

    @Inject
    public MedicineDataStoreFactory(Context context, MedicineCache medicineCache, MedicineEntityDataMapper medicineEntityDataMapper) {
        this.context = context;
        this.medicineCache = medicineCache;
        this.medicineEntityDataMapper = medicineEntityDataMapper;
    }

    public MedicineDataStore create(int medicineId) {
        MedicineDataStore medicineDataStore;

        if (!this.medicineCache.isExpired() && this.medicineCache.isCached(medicineId)) {
            //TODO: vamos a suar Cache?
            //medicineDataStore = new DiskMedicineDataStore(this.medicineCache);
            medicineDataStore = createDatabaseDataStore();
        } else {
            medicineDataStore = createDatabaseDataStore();
        }

        return medicineDataStore;
    }

    public MedicineDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        MedicineDatabaseAPI dataBaseAPI = new MedicineDatabaseAPIImpl(this.context);
        return new DatabaseMedicineDataStore(this.medicineCache, dataBaseAPI, medicineEntityDataMapper);
    }
}
