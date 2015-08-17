package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

public class MedicineEntity {

    @SerializedName("id")
    private int medicineId;

    @SerializedName("name")
    private String name;

    @SerializedName("firstDay")
    private String firstDay;

    @SerializedName("firstHour")
    private String firstHour;

    @SerializedName("lastDay")
    private String lastDay;

    @SerializedName("lastHour")
    private String lastHour;

    @SerializedName("interval")
    private String interval;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;


    public MedicineEntity() {
        //empty
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public String getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(String firstHour) {
        this.firstHour = firstHour;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public String getLastHour() {
        return lastHour;
    }

    public void setLastHour(String lastHour) {
        this.lastHour = lastHour;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getDescription() { return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        stringBuilder.append("id=" + this.getMedicineId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("firstDay=" + this.getFirstDay() + "\n");
        stringBuilder.append("firstHour=" + this.getFirstHour() + "\n");
        stringBuilder.append("lastDay=" + this.getLastDay() + "\n");
        stringBuilder.append("lastHour=" + this.getLastHour() + "\n");
        stringBuilder.append("interval=" + this.getInterval() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
