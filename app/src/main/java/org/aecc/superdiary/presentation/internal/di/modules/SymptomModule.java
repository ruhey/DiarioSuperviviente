package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.SymptomListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SymptomModule {

    @Provides
    @PerActivity
    GetSymptomListUseCase provideGetSymptomListUseCase(GetSymptomListUseCaseImpl getSymptomListUseCase) {
        return getSymptomListUseCase;
    }

    @Provides
    @PerActivity
    GetSymptomDetailsUseCase provideGetSymptomDetailsUseCase(GetSymptomDetailsUseCaseImpl getSymptomDetailsUseCase) {
        return getSymptomDetailsUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideSymptomListPresenter(SymptomListPresenter symptomListPresenter) {
        return symptomListPresenter;
    }

}

