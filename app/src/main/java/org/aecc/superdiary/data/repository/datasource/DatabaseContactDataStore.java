package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.database.ContactDatabaseAPI;
import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.data.entity.mapper.ContactEntityDataMapper;
import org.aecc.superdiary.domain.Contact;

import java.util.Collection;

public class DatabaseContactDataStore implements ContactDataStore {
    private final ContactCache contactCache;
    private final ContactDatabaseAPI databaseAPI;
    private final ContactEntityDataMapper contactEntityDataMapper;

    public DatabaseContactDataStore(ContactCache contactCache, ContactDatabaseAPI databaseAPI, ContactEntityDataMapper contactEntityDataMapper) {
        this.contactCache = contactCache;
        this.databaseAPI = databaseAPI;
        this.contactEntityDataMapper = contactEntityDataMapper;

    }

    @Override
    public void getContactsEntityList(final ContactListCallback contactListCallback) {
        this.databaseAPI.getContactEntityList(new ContactDatabaseAPI.ContactListCallback(){

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
        this.databaseAPI.getContactEntityById(id, new ContactDatabaseAPI.ContactDetailsCallback() {

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

    @Override
    public void createContactEntity(final Contact contact, final ContactCreationCallback contactCreationCallback) {
        this.databaseAPI.createContactEntity(this.contactEntityDataMapper.untransform(contact), new ContactDatabaseAPI.ContactCreationCallback(){

            @Override
            public void onContactEntityCreated(ContactEntity contactEntity) {
                contactCreationCallback.onContactCreated(contactEntity);
            }

            @Override
            public void onError(Exception exception) {
                contactCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveContactEntity(final Contact contact, final ContactSaveCallback contactSaveCallback) {
        this.databaseAPI.saveContactEntity(this.contactEntityDataMapper.untransform(contact), new ContactDatabaseAPI.ContactSaveCallback(){
            @Override
            public void onContactEntitySaved(ContactEntity contactEntity) {
                contactSaveCallback.onContactSaved(contactEntity);
            }

            @Override
            public void onError(Exception exception) {
                contactSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteContactEntity(final int id, final ContactDeletionCallback contactDeletionCallback) {
        this.databaseAPI.deleteContactEntity(id, new ContactDatabaseAPI.ContactDeletionCallback(){
            @Override
            public void onContactEntityDeleted(Collection<ContactEntity> contactsCollection) {
                contactDeletionCallback.onContactDeleted(contactsCollection);
            }

            @Override
            public void onError(Exception exception) {
                contactDeletionCallback.onError(exception);
            }
        });
    }


}
