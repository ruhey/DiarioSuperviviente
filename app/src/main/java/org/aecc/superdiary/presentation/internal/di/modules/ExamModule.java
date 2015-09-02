package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.ExamListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ExamModule {

    @Provides
    @PerActivity
    GetExamListUseCase provideGetExamListUseCase(GetExamListUseCaseImpl getExamListUseCase) {
        return getExamListUseCase;
    }

    @Provides
    @PerActivity
    GetExamDetailsUseCase provideGetExamDetailsUseCase(GetExamDetailsUseCaseImpl getExamDetailsUseCase) {
        return getExamDetailsUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideExamListPresenter(ExamListPresenter examListPresenter) {
        return examListPresenter;
    }

}

