package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.exam.CreateExamUseCase;
import org.aecc.superdiary.domain.interactor.exam.DeleteExamUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.exam.SaveExamUseCase;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.ExamListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;
import org.aecc.superdiary.presentation.presenter.ExamDetailCreatePresenter;
import org.aecc.superdiary.presentation.presenter.ExamDetailDeletePresenter;
import org.aecc.superdiary.presentation.presenter.ExamDetailEditPresenter;
import org.aecc.superdiary.presentation.presenter.ExamListPresenter;

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
    CreateExamUseCase provideCreateExamUseCase(CreateExamUseCase createExamUseCase) {
        return createExamUseCase;
    }

    @Provides
    @PerActivity
    DeleteExamUseCase provideDeleteExamUseCase(DeleteExamUseCase deleteExamUseCase) {
        return deleteExamUseCase;
    }

    @Provides
    @PerActivity
    SaveExamUseCase provideSaveExamUseCase(SaveExamUseCase saveExamUseCase) {
        return saveExamUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideExamListPresenter(ExamListPresenter examListPresenter) {
        return examListPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesExamDetailEditPresenter(ExamDetailEditPresenter examDetailEditPresenter){
        return examDetailEditPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesExamDetailDeletePresenter(ExamDetailDeletePresenter examDetailDeletePresenter){
        return examDetailDeletePresenter;
    }

    @Provides
    @PerActivity
    Presenter providesExamDetailCreatePresenter(ExamDetailCreatePresenter examDetailCreatePresenter){
        return examDetailCreatePresenter;
    }

}

