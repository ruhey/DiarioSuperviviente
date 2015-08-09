package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.ContactEntity;

import java.util.Collection;

public interface DatabaseAPI {

    interface ContactListCallback {
        void onContactListLoaded(Collection<ContactEntity> contactsCollection);

        void onError(Exception exception);
    }

    interface ContactDetailsCallback {
        void onContactEntityLoaded(ContactEntity contactEntity);

        void onError(Exception exception);
    }

    void getContactList(ContactListCallback contactListCallback);

    void getContactById(final int contactId, final ContactDetailsCallback contactDetailsCallback);
}
