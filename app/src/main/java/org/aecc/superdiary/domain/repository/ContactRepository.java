package org.aecc.superdiary.domain.repository;

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

    void getContactList(ContactListCallback ContactListCallback);

    void getContactById(final int contactId, ContactDetailsCallback contactCallback);
}
