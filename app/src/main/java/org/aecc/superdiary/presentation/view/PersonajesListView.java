package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ContactModel;

import java.util.Collection;

public interface PersonajesListView extends LoadDataView{

    void renderContactList(Collection<ContactModel> contactModelCollection);

    void viewContact(ContactModel contactModel);
}
