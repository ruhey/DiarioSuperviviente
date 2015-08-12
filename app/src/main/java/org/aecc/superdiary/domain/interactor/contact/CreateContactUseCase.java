package org.aecc.superdiary.domain.interactor.contact;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface CreateContactUseCase extends Interactor {
    public void execute(Contact contact, Callback callback);


    interface Callback {
        void onContactDataCreated(Contact contact);

        void onError(ErrorBundle errorBundle);
    }
}