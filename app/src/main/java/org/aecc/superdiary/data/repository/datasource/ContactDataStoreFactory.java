package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.database.ContactDatabaseAPI;
import org.aecc.superdiary.data.database.ContactDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.ContactEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactDataStoreFactory {

    private final Context context;
    private final ContactCache contactCache;
    private final ContactEntityDataMapper contactEntityDataMapper;

    @Inject
    public ContactDataStoreFactory(Context context, ContactCache contactCache, ContactEntityDataMapper contactEntityDataMapper) {
        this.context = context;
        this.contactCache = contactCache;
        this.contactEntityDataMapper = contactEntityDataMapper;
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
        return new DatabaseContactDataStore(this.contactCache, dataBaseAPI, contactEntityDataMapper);
    }
}
