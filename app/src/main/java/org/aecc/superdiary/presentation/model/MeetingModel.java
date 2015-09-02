package org.aecc.superdiary.presentation.model;


public class MeetingModel {

    private final int meetingId;
    private String name;
    private String place;
    private String questions;
    private String dateMeeting;
    private String hourMeeting;
    private String dateAlarm;
    private String hourAlarm;
    private String duration;
    private String image;

    public MeetingModel(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getMeetingId() {
        return meetingId;
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

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getDateMeeting() {
        return dateMeeting;
    }

    public void setDateMeeting(String dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public String getHourMeeting() {
        return hourMeeting;
    }

    public void setHourMeeting(String hourMeeting) {
        this.hourMeeting = hourMeeting;
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
        stringBuilder.append("id=" + this.getMeetingId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("place=" + this.getPlace() + "\n");
        stringBuilder.append("questions=" + this.getQuestions() + "\n");
        stringBuilder.append("dayMeeting=" + this.getDateMeeting() + "\n");
        stringBuilder.append("hourMeeting=" + this.getHourMeeting() + "\n");
        stringBuilder.append("dateAlarm=" + this.getDateAlarm() + "\n");
        stringBuilder.append("hourAlarm=" + this.getHourAlarm() + "\n");
        stringBuilder.append("duration=" + this.getDuration() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
