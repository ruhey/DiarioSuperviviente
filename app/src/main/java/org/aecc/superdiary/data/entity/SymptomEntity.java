package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SymptomEntity {

    @SerializedName("id")
    private int symptomId;

    @SerializedName("name")
    private String name;

    @SerializedName("dateSymptom")
    private String dateSymptom;

    @SerializedName("hourSymptom")
    private String hourSymptom;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    public SymptomEntity() {
        //empty
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateSymptom() {
        return dateSymptom;
    }

    public void setDateSymptom(String dateSymptom) {
        this.dateSymptom = dateSymptom;
    }

    public String getHourSymptom() {
        return hourSymptom;
    }

    public void setHourSymptom(String hourSymptom) {
        this.hourSymptom = hourSymptom;
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
        stringBuilder.append("id=" + this.getSymptomId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("Date=" + this.getDateSymptom() + "\n");
        stringBuilder.append("hour=" + this.getHourSymptom() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
