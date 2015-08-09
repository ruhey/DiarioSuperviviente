package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.database.DatabaseAPI;
import org.aecc.superdiary.data.database.DatabaseAPIImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactDataStoreFactory {

    private final Context context;
    private final ContactCache contactCache;

    @Inject
    public ContactDataStoreFactory(Context context, ContactCache contactCache){
        this.context = context;
        this.contactCache = contactCache;
    }

    public ContactDataStore create(int userId) {
        ContactDataStore contactDataStore;

        if (!this.contactCache.isExpired() && this.contactCache.isCached(userId)) {
            //TODO: vamos a suar Cache?
            //contactDataStore = new DiskContactDataStore(this.contactCache);
            contactDataStore = createDatabaseDataStore();
        } else {
            contactDataStore = createDatabaseDataStore();
        }

        return contactDataStore;
    }

    public ContactDataStore createDatabaseDataStore() {
       //TODO: implementar mejjor esta creacion
        DatabaseAPI dataBaseAPI = new DatabaseAPIImpl(this.context);
        return new DatabaseContactDataStore(this.contactCache, dataBaseAPI);
    }
}
