package org.aecc.superdiary.presentation.model;


public class ContactModel {

    final private int contactId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String image;
    private String category;

    public ContactModel(int contactId){
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getContactId() {
        return contactId;
    }


    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Details *****\n");
        stringBuilder.append("id=" + this.getContactId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("surname=" + this.getSurname() + "\n");
        stringBuilder.append("email=" + this.getEmail() + "\n");
        stringBuilder.append("phone=" + this.getPhone() + "\n");
        stringBuilder.append("image=" + this.getImage() + "\n");
        stringBuilder.append("category=" + this.getCategory() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
