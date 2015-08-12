package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.database.ContactDatabaseAPI;
import org.aecc.superdiary.data.database.ContactDatabaseAPIImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactDataStoreFactory {

    private final Context context;
    private final ContactCache contactCache;

    @Inject
    public ContactDataStoreFactory(Context context, ContactCache contactCache) {
        this.context = context;
        this.contactCache = contactCache;
    }

    public ContactDataStore create(int contactId) {
        ContactDataStore contactDataStore;

        if (!this.contactCache.isExpired() && this.contactCache.isCached(contactId)) {
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
        ContactDatabaseAPI dataBaseAPI = new ContactDatabaseAPIImpl(this.context);
        return new DatabaseContactDataStore(this.contactCache, dataBaseAPI);
    }
}
