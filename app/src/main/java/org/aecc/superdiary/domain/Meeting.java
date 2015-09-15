package org.aecc.superdiary.domain;

public class Meeting {
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
    private String contactId;
    private String medicationId;
    private String testId;

    public String getSympthomId() {
        return sympthomId;
    }

    public void setSympthomId(String sympthomId) {
        this.sympthomId = sympthomId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    private String sympthomId;

    public Meeting(int meetingId) {
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
        stringBuilder.append("contactId=" + this.getContactId() + "\n");
        stringBuilder.append("medicationId=" + this.getMedicationId() + "\n");
        stringBuilder.append("testId=" + this.getTestId() + "\n");
        stringBuilder.append("sympthomId=" + this.getSympthomId() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
