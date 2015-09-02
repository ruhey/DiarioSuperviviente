package org.aecc.superdiary.presentation.model;


public class SymptomModel {

    private final int symptomId;
    private String name;
    private String dateSymptom;
    private String hourSymptom;
    private String description;
    private String image;

    public SymptomModel(int symptomId) {
        this.symptomId = symptomId;
    }

    public int getSymptomId() {
        return symptomId;
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
