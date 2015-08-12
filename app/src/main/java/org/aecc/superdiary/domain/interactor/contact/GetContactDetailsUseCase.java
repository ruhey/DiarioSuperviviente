package org.aecc.superdiary.domain.interactor.contact;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetContactDetailsUseCase extends Interactor {
    interface Callback {
        void onContactDataLoaded(Contact contact);
        void onError(ErrorBundle errorBundle);
    }


    void execute(int contactId, Callback callback);
}
