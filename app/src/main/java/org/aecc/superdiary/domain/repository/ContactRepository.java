package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface ContactRepository {

    interface ContactListCallback {
        void onContactListLoaded(Collection<Contact> contactsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface ContactDetailsCallback {
        void onContactLoaded(Contact contact);

        void onError(ErrorBundle errorBundle);
    }

    interface ContactCreationCallback {
        void onContactCreated(Contact contact);

        void onError(ErrorBundle errorBundle);
    }

    interface ContactSaveCallback {
        void onContactSaved(Contact contact);

        void onError(ErrorBundle errorBundle);
    }

    interface ContactDetionCallback {
        void onContactDeleted(Collection<Contact> contactsCollection);

        void onError(ErrorBundle errorBundle);
    }

    void getContactList(ContactListCallback ContactListCallback);

    void getContactById(final int contactId, ContactDetailsCallback contactCallback);

    void createContact(final Contact contact, ContactCreationCallback contactCallback);

    void saveContact(final Contact contact, ContactSaveCallback contactCallback);

    void deleteContact(final int contactId, ContactDetionCallback contactDeletionCallback);
}
