package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExamEntity {

    @SerializedName("id")
    private int examId;

    @SerializedName("name")
    private String name;

    @SerializedName("dateExam")
    private String dateExam;

    @SerializedName("hourExam")
    private String hourExam;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    public ExamEntity() {
        //empty
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateExam() {
        return dateExam;
    }

    public void setDateExam(String dateExam) {
        this.dateExam = dateExam;
    }

    public String getHourExam() {
        return hourExam;
    }

    public void setHourExam(String hourExam) {
        this.hourExam = hourExam;
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
        stringBuilder.append("id=" + this.getExamId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("Date=" + this.getDateExam() + "\n");
        stringBuilder.append("hour=" + this.getHourExam() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
