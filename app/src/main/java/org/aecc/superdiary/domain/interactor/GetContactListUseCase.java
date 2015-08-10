package org.aecc.superdiary.domain.interactor;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;


public interface GetContactListUseCase extends Interactor{

    interface Callback {
        void onContactListLoaded(Collection<Contact> contactsCollection);
        void onError(ErrorBundle errorBundle);
    }

    void execute(Callback callback);
}
