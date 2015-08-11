package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.ContactEntity;

import java.util.Collection;

public interface ContactDatabaseAPI {

    interface ContactListCallback {
        void onContactListLoaded(Collection<ContactEntity> contactsCollection);

        void onError(Exception exception);
    }

    interface ContactDetailsCallback {
        void onContactEntityLoaded(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    interface ContactCreationCallback {
        void onContactEntityCreated(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    interface ContactSaveCallback {
        void onContactEntitySaved(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    interface ContactDeletionCallback {
        void onContactEntityDeleted(Collection<ContactEntity> contactsCollection);

        void onError(Exception exception);
    }

    void getContactEntityList(final ContactListCallback contactListCallback);

    void getContactEntityById(final int contactId, final ContactDetailsCallback contactDetailsCallback);

    void createContactEntity(final ContactEntity contact, final ContactCreationCallback contactCreationCallback);

    void saveContactEntity(final ContactEntity contact, final ContactSaveCallback contactSaveCallback);

    void deleteContactEntity(final int contactId, final ContactDeletionCallback contactDeletionCallback);
}
