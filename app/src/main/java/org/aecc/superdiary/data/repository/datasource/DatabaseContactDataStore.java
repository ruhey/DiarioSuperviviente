package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.database.DatabaseAPI;
import org.aecc.superdiary.data.entity.ContactEntity;

import java.util.Collection;

public class DatabaseContactDataStore implements ContactDataStore {
    private final ContactCache contactCache;
    private final DatabaseAPI databaseAPI;

    public DatabaseContactDataStore(ContactCache contactCache, DatabaseAPI databaseAPI) {
        this.contactCache = contactCache;
        this.databaseAPI = databaseAPI;
    }

    @Override
    public void getContactsEntityList(final ContactListCallback contactListCallback) {
        this.databaseAPI.getContactList(new DatabaseAPI.ContactListCallback(){

            @Override
            public void onContactListLoaded(Collection<ContactEntity> contactsCollection) {
                contactListCallback.onContactListLoaded(contactsCollection);
            }

            @Override
            public void onError(Exception exception) {
                contactListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getContactEntityDetails(int id, final ContactDetailsCallback contactDetailsCallback) {
        this.databaseAPI.getContactById(id, new DatabaseAPI.ContactDetailsCallback() {

            @Override
            public void onContactEntityLoaded(ContactEntity contactEntity) {
                contactDetailsCallback.onContactEntityLoaded(contactEntity);
                //CloudUserDataStore.this.putContactEntityInCache(contactEntity);
            }

            @Override
            public void onError(Exception exception) {
                contactDetailsCallback.onError(exception);
            }
        });
    }

    public void putContactEntityInDatabase(ContactEntity contactEntity){
        //TODO: escribir metodo de guardado
    }

    private void putContactEntityInCache(ContactEntity contactEntity) {
        if (contactEntity != null) {
            this.contactCache.put(contactEntity);
        }
    }
}
