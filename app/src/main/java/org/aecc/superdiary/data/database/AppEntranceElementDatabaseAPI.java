package org.aecc.superdiary.data.database;


import org.aecc.superdiary.domain.AppEntranceElement;

import java.util.Collection;

public interface AppEntranceElementDatabaseAPI {


    void getAppEntranceElementList(final AppEntranceElementListCallback AppEntranceElementListCallback);

    interface AppEntranceElementListCallback {

        void onContactListLoaded(Collection<AppEntranceElement> appEntranceElementsCollection);

        void onError(Exception exception);
    }

}
