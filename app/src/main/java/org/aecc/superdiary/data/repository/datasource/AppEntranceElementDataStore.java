package org.aecc.superdiary.data.repository.datasource;


import org.aecc.superdiary.domain.AppEntranceElement;

import java.util.Collection;

public interface AppEntranceElementDataStore {

    void getappEntranceElementList(AppEntranceElementListCallback appEntranceElementListCallback);



    interface AppEntranceElementListCallback {
        void appEntranceElementListLoaded(Collection<AppEntranceElement> appEntranceElementCollection);

        void onError(Exception exception);
    }
}
