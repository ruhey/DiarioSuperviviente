package org.aecc.superdiary.domain;

public class AppEntranceElement {

    private String type;
    private String textToShow;
    private String date;

    public AppEntranceElement (){
        //empty
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTextToShow() {
        return textToShow;
    }

    public void setTextToShow(String textToShow) {
        this.textToShow = textToShow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
