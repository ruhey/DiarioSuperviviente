package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ContactModel;

public interface PersonajeDetailView extends LoadDataView {

    void renderContact(ContactModel contact);

    void showOKMessage();

    void goBack();
}
