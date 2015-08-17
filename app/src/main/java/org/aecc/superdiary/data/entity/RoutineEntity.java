package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

public class RoutineEntity {

    @SerializedName("id")
    private int routineId;

    @SerializedName("name")
    private String name;

    @SerializedName("place")
    private String place;

    @SerializedName("description")
    private String description;

    @SerializedName("dateRoutine")
    private String dateRoutine;

    @SerializedName("hourRoutine")
    private String hourRoutine;

    @SerializedName("dateAlarm")
    private String dateAlarm;

    @SerializedName("hourAlarm")
    private String hourAlarm;

    @SerializedName("duration")
    private String duration;

    @SerializedName("satisfaction")
    private String satisfaction;

    @SerializedName("image")
    private String image;


    public RoutineEntity() {
        //empty
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRoutine() {
        return dateRoutine;
    }

    public void setDateRoutine(String dateRoutine) {
        this.dateRoutine = dateRoutine;
    }

    public String getHourRoutine() {
        return hourRoutine;
    }

    public void setHourRoutine(String hourRoutine) {
        this.hourRoutine = hourRoutine;
    }

    public String getDateAlarm() {
        return dateAlarm;
    }

    public void setDateAlarm(String dateAlarm) {
        this.dateAlarm = dateAlarm;
    }

    public String getHourAlarm() {
        return hourAlarm;
    }

    public void setHourAlarm(String hourAlarm) {
        this.hourAlarm = hourAlarm;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("id=" + this.getRoutineId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("place=" + this.getPlace() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("dayRoutine=" + this.getDateRoutine() + "\n");
        stringBuilder.append("hourRoutine=" + this.getHourRoutine() + "\n");
        stringBuilder.append("dateAlarm=" + this.getDateAlarm() + "\n");
        stringBuilder.append("hourAlarm=" + this.getHourAlarm() + "\n");
        stringBuilder.append("duration=" + this.getDuration() + "\n");
        stringBuilder.append("satisfaction=" + this.getSatisfaction() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
