package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.GetContactDetailsUseCase;
import org.aecc.superdiary.domain.interactor.GetContactDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.GetContactListUseCase;
import org.aecc.superdiary.domain.interactor.GetContactListUseCaseImpl;
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

