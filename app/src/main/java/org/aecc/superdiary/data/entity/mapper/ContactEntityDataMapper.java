package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.domain.Contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ContactEntityDataMapper {

    @Inject
    public ContactEntityDataMapper(){}

    public Contact transform(ContactEntity contactEntity) {
        Contact contact = null;

        if (contactEntity != null){
            contact = new Contact(contactEntity.getContactId());
            contact.setName(contactEntity.getName());
            contact.setSurname(contactEntity.getSurname());
            contact.setEmail(contactEntity.getEmail());
            //TODO:ver como hacer lo de la imagen
            contact.setImage(contactEntity.getImage());
            contact.setPhone(contactEntity.getPhone());
            //TODO: ver como va lo de los enumerados
            contact.setCategory(contactEntity.getCategoryType());
        }
        return contact;
    }

    public ContactEntity untransform(Contact contact) {
        ContactEntity contactEntity = null;

        if (contact != null){
            contactEntity.setContactId(contact.getContactId());
            contactEntity.setName(contact.getName());
            contactEntity.setSurname(contact.getSurname());
            contactEntity.setEmail(contact.getEmail());
            //TODO:ver como hacer lo de la imagen
            contactEntity.setImage(contact.getImage());
            contactEntity.setPhone(contact.getPhone());
            //TODO: ver como va lo de los enumerados
            contactEntity.setCategoryType(contact.getCategory());
        }
        return contactEntity;
    }

    public Collection<Contact> transform(Collection<ContactEntity> contactEntityCollection){
        List<Contact> contactList = new ArrayList<Contact>();
        Contact contact;
        for(ContactEntity contactEntity : contactEntityCollection){
            contact = transform(contactEntity);
            if (contact != null) {
                contactList.add(contact);
            }
        }
        return contactList;
    }

    public Collection<ContactEntity> untransform(Collection<Contact> contactCollection){
        List<ContactEntity> contactEntityList = new ArrayList<ContactEntity>();
        ContactEntity contactEntity;
        for(Contact contact : contactCollection){
            contactEntity = untransform(contact);
            if (contactEntity != null) {
                contactEntityList.add(contactEntity);
            }
        }
        return contactEntityList;
    }
}
