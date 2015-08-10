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
}
