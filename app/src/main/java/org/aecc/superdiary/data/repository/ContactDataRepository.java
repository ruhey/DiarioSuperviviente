package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.data.entity.mapper.ContactEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateContactException;
import org.aecc.superdiary.data.exception.CantSaveContactException;
import org.aecc.superdiary.data.exception.ContactNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.ContactDataStore;
import org.aecc.superdiary.data.repository.datasource.ContactDataStoreFactory;
import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.repository.ContactRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactDataRepository implements ContactRepository {

    private final ContactDataStoreFactory contactDataStoreFactory;
    private final ContactEntityDataMapper contactEntityDataMapper;

    @Inject
    public ContactDataRepository(ContactDataStoreFactory contactDataStoreFactory, ContactEntityDataMapper contactEntityDataMapper) {
        this.contactDataStoreFactory = contactDataStoreFactory;
        this.contactEntityDataMapper = contactEntityDataMapper;
    }

    @Override
    public void getContactList(final ContactListCallback contactListCallback) {
        final ContactDataStore contactDataStore = this.contactDataStoreFactory.createDatabaseDataStore();
        contactDataStore.getContactsEntityList(new ContactDataStore.ContactListCallback() {
            @Override
            public void onContactListLoaded(Collection<ContactEntity> contactsCollection) {
                Collection<Contact> contacts =
                        ContactDataRepository.this.contactEntityDataMapper.transform(contactsCollection);
                contactListCallback.onContactListLoaded(contacts);
            }

            @Override
            public void onError(Exception exception) {
                contactListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getContactById(final int contactId, final ContactDetailsCallback contactCallback) {
        ContactDataStore contactDataStore = this.contactDataStoreFactory.create(contactId);
        contactDataStore.getContactEntityDetails(contactId, new ContactDataStore.ContactDetailsCallback() {
            @Override
            public void onContactEntityLoaded(ContactEntity contactEntity) {
                Contact contact = ContactDataRepository.this.contactEntityDataMapper.transform(contactEntity);
                if (contact != null) {
                    contactCallback.onContactLoaded(contact);
                } else {
                    contactCallback.onError(new RepositoryErrorBundle(new ContactNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                contactCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createContact(final Contact contact, final ContactCreationCallback contactCreateCallback) {
        final ContactDataStore contactDataStore = this.contactDataStoreFactory.createDatabaseDataStore();
        contactDataStore.createContactEntity(contact, new ContactDataStore.ContactCreationCallback() {

            @Override
            public void onContactCreated(ContactEntity contactEntity) {
                Contact contact = ContactDataRepository.this.contactEntityDataMapper.transform(contactEntity);
                if (contact != null) {
                    contactCreateCallback.onContactCreated(contact);
                } else {
                    contactCreateCallback.onError(new RepositoryErrorBundle(new CantCreateContactException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                contactCreateCallback.onError(new RepositoryErrorBundle(new CantCreateContactException()));
            }
        });
    }

    @Override
    public void saveContact(final Contact contact, final ContactSaveCallback contactSaveCallback) {
        final ContactDataStore contactDataStore = this.contactDataStoreFactory.createDatabaseDataStore();
        contactDataStore.saveContactEntity(contact, new ContactDataStore.ContactSaveCallback() {

            @Override
            public void onContactSaved(ContactEntity contactEntity) {
                Contact contact = ContactDataRepository.this.contactEntityDataMapper.transform(contactEntity);
                if (contact != null) {
                    contactSaveCallback.onContactSaved(contact);
                } else {
                    contactSaveCallback.onError(new RepositoryErrorBundle(new CantSaveContactException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                contactSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteContact(final int contactId, final ContactDetionCallback contactDeletionCallback) {
        final ContactDataStore contactDataStore = this.contactDataStoreFactory.createDatabaseDataStore();
        contactDataStore.deleteContactEntity(contactId, new ContactDataStore.ContactDeletionCallback() {
            @Override
            public void onContactDeleted(Collection<ContactEntity> contactsCollection) {
                Collection<Contact> contacts =
                        ContactDataRepository.this.contactEntityDataMapper.transform(contactsCollection);
                contactDeletionCallback.onContactDeleted(contacts);
            }

            @Override
            public void onError(Exception exception) {
                contactDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
