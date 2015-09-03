package org.aecc.superdiary.presentation.view;

import org.aecc.superdiary.presentation.model.ContactModel;


public interface PersonajeDetailNoEditView extends LoadDataView{

    void renderContact(ContactModel contact);

    void editContact(int contactId);

    void deleteContact(int contactId);
}
