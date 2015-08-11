package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.domain.Contact;

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

    interface ContactCreationCallback {
        void onContactCreated(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    interface ContactSaveCallback {
        void onContactSaved(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    interface ContactDeletionCallback {
        void onContactDeleted(Collection<ContactEntity> contactsCollection);

        void onError(Exception exception);
    }

    void getContactsEntityList(ContactListCallback contactListCallback);

    void getContactEntityDetails(int id, ContactDetailsCallback contactDetailsCallback);

    void createContactEntity(Contact contact, ContactCreationCallback contactCallback);

    void saveContactEntity(Contact contact, ContactSaveCallback contactCallback);

    void deleteContactEntity(int id, ContactDeletionCallback contactDeletionCallback);
}
