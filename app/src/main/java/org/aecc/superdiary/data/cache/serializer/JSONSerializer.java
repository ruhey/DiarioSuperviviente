package org.aecc.superdiary.data.cache.serializer;

import com.google.gson.Gson;

import org.aecc.superdiary.data.entity.ContactEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JSONSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JSONSerializer() {}

    public String serializeContact(ContactEntity contactEntity) {
        String jsonString = gson.toJson(contactEntity, ContactEntity.class);
        return jsonString;
    }

    public ContactEntity deserializeContact(String jsonString) {
        ContactEntity contactEntity = gson.fromJson(jsonString, ContactEntity.class);
        return contactEntity;
    }
}
