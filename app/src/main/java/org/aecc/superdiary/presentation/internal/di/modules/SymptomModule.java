package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.symptom.CreateSymptomUseCase;
import org.aecc.superdiary.domain.interactor.symptom.DeleteSymptomUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.symptom.SaveSymptomUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.SymptomDetailCreatePresenter;
import org.aecc.superdiary.presentation.presenter.SymptomDetailDeletePresenter;
import org.aecc.superdiary.presentation.presenter.SymptomDetailEditPresenter;
import org.aecc.superdiary.presentation.presenter.SymptomListPresenter;
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
    CreateSymptomUseCase provideCreateSymptomUseCase(CreateSymptomUseCase createSymptomUseCase) {
        return createSymptomUseCase;
    }

    @Provides
    @PerActivity
    DeleteSymptomUseCase provideDeleteSymptomUseCase(DeleteSymptomUseCase deleteSymptomUseCase) {
        return deleteSymptomUseCase;
    }

    @Provides
    @PerActivity
    SaveSymptomUseCase provideSaveSymptomUseCase(SaveSymptomUseCase saveSymptomUseCase) {
        return saveSymptomUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideSymptomListPresenter(SymptomListPresenter symptomListPresenter) {
        return symptomListPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesSymptomDetailEditPresenter(SymptomDetailEditPresenter symptomDetailEditPresenter){
        return symptomDetailEditPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesSymptomDetailDeletePresenter(SymptomDetailDeletePresenter symptomDetailDeletePresenter){
        return symptomDetailDeletePresenter;
    }

    @Provides
    @PerActivity
    Presenter providesSymptomDetailCreatePresenter(SymptomDetailCreatePresenter symptomDetailCreatePresenter){
        return symptomDetailCreatePresenter;
    }

}

