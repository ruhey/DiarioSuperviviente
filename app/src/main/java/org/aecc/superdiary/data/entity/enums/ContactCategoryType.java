package org.aecc.superdiary.data.entity.enums;


public enum ContactCategoryType {

    FAMILY(1, "Familia"), PERSONAL(2, "Personal de la AECC"), PROFESIONAL(3, "Profesionales"), OTROS(4, "Otros");

    private int value;
    private String literal;

    private ContactCategoryType(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return this.value;
    }

    public String getLiteral() {
        return this.literal;
    }
}
