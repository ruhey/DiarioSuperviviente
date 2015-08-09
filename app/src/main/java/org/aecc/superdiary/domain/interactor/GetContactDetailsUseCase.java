package org.aecc.superdiary.domain.interactor;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;


public interface GetContactDetailsUseCase extends Interactor {
    interface Callback {
        void onContactDataLoaded(Contact contact);
        void onError(ErrorBundle errorBundle);
    }


    public void execute(int contactId, Callback callback);
}
