package org.aecc.superdiary.presentation.model;


public class MedicineModel {

    private final int medicineId;
    private String name;
    private String firstDay;
    private String firstHour;
    private String lastDay;
    private String lastHour;
    private String interval;
    private String description;
    private String image;

    public MedicineModel(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getMedicineId() {
        return this.medicineId;
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

    public String getDescription() {
        return description;
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
