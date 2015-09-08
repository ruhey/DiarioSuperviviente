package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.routine.CreateRoutineUseCase;
import org.aecc.superdiary.domain.interactor.routine.CreateRoutineUseCaseImpl;
import org.aecc.superdiary.domain.interactor.routine.DeleteRoutineUseCase;
import org.aecc.superdiary.domain.interactor.routine.DeleteRoutineUseCaseImpl;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.routine.SaveRoutineUseCase;
import org.aecc.superdiary.domain.interactor.routine.SaveRoutineUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.RoutineDetailCreatePresenter;
import org.aecc.superdiary.presentation.presenter.RoutineDetailDeletePresenter;
import org.aecc.superdiary.presentation.presenter.RoutineDetailEditPresenter;
import org.aecc.superdiary.presentation.presenter.RoutineListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RoutineModule {

    @Provides
    @PerActivity
    GetRoutineListUseCase provideGetRoutineListUseCase(GetRoutineListUseCaseImpl getRoutineListUseCase) {
        return getRoutineListUseCase;
    }

    @Provides
    @PerActivity
    GetRoutineDetailsUseCase provideGetRoutineDetailsUseCase(GetRoutineDetailsUseCaseImpl getRoutineDetailsUseCase) {
        return getRoutineDetailsUseCase;
    }

    @Provides
    @PerActivity
    CreateRoutineUseCase provideCreateRoutineUseCase(CreateRoutineUseCaseImpl createRoutineUseCase) {
        return createRoutineUseCase;
    }

    @Provides
    @PerActivity
    DeleteRoutineUseCase provideDeleteRoutineUseCase(DeleteRoutineUseCaseImpl deleteRoutineUseCase) {
        return deleteRoutineUseCase;
    }

    @Provides
    @PerActivity
    SaveRoutineUseCase provideSaveRoutineUseCase(SaveRoutineUseCaseImpl saveRoutineUseCase) {
        return saveRoutineUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideRoutineListPresenter(RoutineListPresenter routineListPresenter) {
        return routineListPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesRoutineDetailEditPresenter(RoutineDetailEditPresenter routineDetailEditPresenter){
        return routineDetailEditPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesRoutineDetailDeletePresenter(RoutineDetailDeletePresenter routineDetailDeletePresenter){
        return routineDetailDeletePresenter;
    }

    @Provides
    @PerActivity
    Presenter providesRoutineDetailCreatePresenter(RoutineDetailCreatePresenter routineDetailCreatePresenter){
        return routineDetailCreatePresenter;
    }

}

