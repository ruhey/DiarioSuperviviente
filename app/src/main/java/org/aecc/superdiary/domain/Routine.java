package org.aecc.superdiary.domain;

public class Routine {
    private final int routineId;
    private String name;
    private String place;
    private String description;
    private String dateRoutine;
    private String hourRoutine;
    private String dateAlarm;
    private String hourAlarm;
    private String duration;
    private String satisfaction;
    private String image;

    public Routine(int routineId) {
        this.routineId = routineId;
    }

    public int getRoutineId() {
        return routineId;
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

    public void setDescription(String questions) {
        this.description = questions;
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
        stringBuilder.append("questions=" + this.getDescription() + "\n");
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
