package org.aecc.superdiary.domain.interactor.contact;


import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteContactUseCase extends Interactor {
    void execute(int contactId, Callback callback);

    interface Callback {
        void onContactDataDeleted(Collection<Contact> contactsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
