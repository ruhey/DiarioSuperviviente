package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.ContactEntity;

import java.util.Collection;

public interface ContactDataStore {

    interface ContactListCallback {
        void onContactListLoaded(Collection<ContactEntity> contactsCollection);

        void onError(Exception exception);
    }

    interface ContactDetailsCallback {
        void onContactEntityLoaded(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    void getContactsEntityList(ContactListCallback contactListCallback);

    void getContactEntityDetails(int id, ContactDetailsCallback contactDetailsCallback);
}
