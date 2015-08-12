package org.aecc.superdiary.domain.interactor.contact;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetContactListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onContactListLoaded(Collection<Contact> contactsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
