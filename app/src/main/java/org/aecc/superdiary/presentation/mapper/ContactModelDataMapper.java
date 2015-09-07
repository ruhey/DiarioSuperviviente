package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.ContactModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class ContactModelDataMapper {

    @Inject
    public ContactModelDataMapper() {
    }

    public ContactModel transform(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ContactModel contactModel = new ContactModel(contact.getContactId());
        contactModel.setName(contact.getName());
        contactModel.setSurname(contact.getSurname());
        contactModel.setEmail(contact.getEmail());
        contactModel.setPhone(contact.getPhone());
        contactModel.setImage(contact.getImage());
        contactModel.setCategory(contact.getCategory());

        return contactModel;
    }

    public Contact untransform(ContactModel contactModel){
        if (contactModel == null){
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        Contact contact = new Contact(contactModel.getContactId());
        contact.setName(contactModel.getName());
        contact.setSurname(contactModel.getSurname());
        contact.setEmail(contactModel.getEmail());
        contact.setPhone(contactModel.getPhone());
        contact.setImage(contactModel.getImage());
        contact.setCategory(contactModel.getCategory());

        return contact;
    }

    public Collection<ContactModel> transform(Collection<Contact> contactsCollection) {
        Collection<ContactModel> contactModelsCollection;

        if (contactsCollection != null && !contactsCollection.isEmpty()) {
            contactModelsCollection = new ArrayList<ContactModel>();
            for (Contact contact : contactsCollection) {
                contactModelsCollection.add(transform(contact));
            }
        } else {
            contactModelsCollection = Collections.emptyList();
        }

        return contactModelsCollection;
    }
}
