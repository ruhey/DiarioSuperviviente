package org.aecc.superdiary.data.repository.datasource;


import android.content.Context;

import org.aecc.superdiary.data.database.AppEntranceElementDatabaseAPI;
import org.aecc.superdiary.data.database.AppEntranceElementDatabaseAPIImpl;

import javax.inject.Inject;

public class AppEntranceElementDataStoreFactory {

    private final Context context;



    @Inject
    public AppEntranceElementDataStoreFactory(Context context) {
        this.context = context;


    }

    public AppEntranceElementDataStore create(int appEntranceElementId) {
        AppEntranceElementDataStore appEntranceElementDataStore;


            //appEntranceElementDataStore = new DiskAppEntranceElementDataStore(this.appEntranceElementCache);
            appEntranceElementDataStore = createDatabaseDataStore();


        return appEntranceElementDataStore;
    }

    public AppEntranceElementDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        AppEntranceElementDatabaseAPI dataBaseAPI = new AppEntranceElementDatabaseAPIImpl(this.context);
        return new DatabaseAppEntranceElementDataStore(dataBaseAPI);
    }
}
