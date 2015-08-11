package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.contact.GetContactListUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;

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
}

