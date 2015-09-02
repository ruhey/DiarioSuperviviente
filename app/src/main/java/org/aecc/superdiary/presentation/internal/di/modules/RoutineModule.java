package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
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
    Presenter provideRoutineListPresenter(RoutineListPresenter routineListPresenter) {
        return routineListPresenter;
    }

}

