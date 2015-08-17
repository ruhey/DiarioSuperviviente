package org.aecc.superdiary.data.cache.serializer;

import com.google.gson.Gson;

import org.aecc.superdiary.data.entity.CommentEntity;
import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.data.entity.ExamEntity;
import org.aecc.superdiary.data.entity.MeetingEntity;
import org.aecc.superdiary.data.entity.RoutineEntity;
import org.aecc.superdiary.data.entity.MedicineEntity;
import org.aecc.superdiary.data.entity.SymptomEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JSONSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JSONSerializer() {
    }

    public String serializeContact(ContactEntity contactEntity) {
        return gson.toJson(contactEntity, ContactEntity.class);
    }

    public ContactEntity deserializeContact(String jsonString) {
        return gson.fromJson(jsonString, ContactEntity.class);
    }

    public String serializeMeeting(MeetingEntity meetingEntity) {
        return gson.toJson(meetingEntity, MeetingEntity.class);
    }

    public MeetingEntity deserializeMeeting(String jsonString) {
        return gson.fromJson(jsonString, MeetingEntity.class);
    }

    public String serializeRoutine(RoutineEntity routineEntity) {
        return gson.toJson(routineEntity, RoutineEntity.class);
    }

    public RoutineEntity deserializeRoutine(String jsonString) {
        return gson.fromJson(jsonString, RoutineEntity.class);
    }

    public String serializeMedicine(MedicineEntity medicineEntity) {
        return gson.toJson(medicineEntity, MedicineEntity.class);
    }

    public MedicineEntity deserializeMedicine(String jsonString) {
        return gson.fromJson(jsonString, MedicineEntity.class);
    }

    public String serializeSymptom(SymptomEntity symptomEntity) {
        return gson.toJson(symptomEntity, SymptomEntity.class);
    }

    public SymptomEntity deserializeSymptom(String jsonString) {
        return gson.fromJson(jsonString, SymptomEntity.class);
    }

    public String serializeExam(ExamEntity examEntity) {
        return gson.toJson(examEntity, ExamEntity.class);
    }

    public ExamEntity deserializeExam(String jsonString) {
        return gson.fromJson(jsonString, ExamEntity.class);
    }

    public String serializeComment(CommentEntity commentEntity) {
        return gson.toJson(commentEntity, CommentEntity.class);
    }

    public CommentEntity deserializeComment(String jsonString) {
        return gson.fromJson(jsonString, CommentEntity.class);
    }
}
