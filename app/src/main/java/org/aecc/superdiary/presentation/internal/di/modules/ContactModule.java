package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.contact.GetContactListUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.ContactDetailsPresenter;
import org.aecc.superdiary.presentation.presenter.ContactListPresenter;
import org.aecc.superdiary.presentation.presenter.ContactsDetailsNoEditPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactModule {

    @Provides
    @PerActivity
    GetContactListUseCase provideGetContactListUseCase(GetContactListUseCaseImpl getContactListUseCase) {
        return getContactListUseCase;
    }

    @Provides
    @PerActivity
    GetContactDetailsUseCase provideGetContactDetailsUseCase(GetContactDetailsUseCaseImpl getContactDetailsUseCase) {
        return getContactDetailsUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideContactListPresenter(ContactListPresenter contactListPresenter) {
        return contactListPresenter;
    }

    @Provides
    @PerActivity
    Presenter provideContactDetailsPresenter(ContactDetailsPresenter contactDetailsPresenter){
        return contactDetailsPresenter;
    }

    @Provides
    @PerActivity
    Presenter provideContactDetailsNoEditPresenter(ContactsDetailsNoEditPresenter contactsDetailsNoEditPresenter){
        return contactsDetailsNoEditPresenter;
    }

}

