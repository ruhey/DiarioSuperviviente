package org.aecc.superdiary.data.cache.serializer;

import com.google.gson.Gson;

import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.data.entity.ContactEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JSONSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JSONSerializer() {}

    public String serializeContact(ContactEntity contactEntity) {
        return gson.toJson(contactEntity, ContactEntity.class);
    }

    public ContactEntity deserializeContact(String jsonString) {
        return gson.fromJson(jsonString, ContactEntity.class);
    }

    public String serializeComment(CommentEntity commentEntity) {
        return gson.toJson(commentEntity, CommentEntity.class);
    }

    public CommentEntity deserializeComment(String jsonString) {
        return gson.fromJson(jsonString, CommentEntity.class);
    }
}
