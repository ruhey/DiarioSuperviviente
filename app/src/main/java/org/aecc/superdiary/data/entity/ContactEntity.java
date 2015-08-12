package org.aecc.superdiary.data.entity;

import com.google.gson.annotations.SerializedName;

public class ContactEntity {

    @SerializedName("id")
    private int contactId;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("image")
    private String image;

    @SerializedName("category_type")
    private String categoryType;

    public ContactEntity() {
        //empty
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("id=" + this.getContactId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("surname=" + this.getSurname() + "\n");
        stringBuilder.append("email=" + this.getEmail() + "\n");
        stringBuilder.append("phone=" + this.getPhone() + "\n");
        stringBuilder.append("imageURL=" + this.getImage() + "\n");
        stringBuilder.append("categoryType=" + this.getCategoryType() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
