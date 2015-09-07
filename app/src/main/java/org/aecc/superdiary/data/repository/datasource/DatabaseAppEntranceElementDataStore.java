package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.database.AppEntranceElementDatabaseAPI;
import org.aecc.superdiary.domain.AppEntranceElement;

import java.util.Collection;

import javax.inject.Inject;

public class DatabaseAppEntranceElementDataStore implements AppEntranceElementDataStore{


    private final AppEntranceElementDatabaseAPI databaseAPI;


    public DatabaseAppEntranceElementDataStore(AppEntranceElementDatabaseAPI databaseAPI) {

        this.databaseAPI = databaseAPI;


    }

    @Override
    public void getappEntranceElementList(final AppEntranceElementListCallback appEntranceElementListCallback) {
        this.databaseAPI.getAppEntranceElementList(new AppEntranceElementDatabaseAPI.AppEntranceElementListCallback(){

            @Override
            public void onContactListLoaded(Collection<AppEntranceElement> appEntranceElementsCollection) {
                appEntranceElementListCallback.appEntranceElementListLoaded(appEntranceElementsCollection);
            }

            @Override
            public void onError(Exception exception) {
                appEntranceElementListCallback.onError(exception);
            }
        });
    }
}
