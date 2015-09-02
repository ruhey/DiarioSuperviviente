package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.RoutineModelDataMapper;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.RutinaDetailView;

import javax.inject.Inject;

public class RoutineDetailsPresenter implements Presenter {
    private final GetRoutineDetailsUseCase getRoutineDetailsUseCase;
    private final RoutineModelDataMapper routineModelDataMapper;
    private int routineId;
    private RutinaDetailView viewDetailsView;
    private final GetRoutineDetailsUseCase.Callback routineDetailsCallback = new GetRoutineDetailsUseCase.Callback() {
        @Override
        public void onRoutineDataLoaded(Routine routine) {
            RoutineDetailsPresenter.this.showRoutineDetailsInView(routine);
            RoutineDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailsPresenter.this.hideViewLoading();
            RoutineDetailsPresenter.this.showErrorMessage(errorBundle);
            RoutineDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public RoutineDetailsPresenter(GetRoutineDetailsUseCase getRoutineDetailsUseCase,
                                   RoutineModelDataMapper routineModelDataMapper) {
        this.getRoutineDetailsUseCase = getRoutineDetailsUseCase;
        this.routineModelDataMapper = routineModelDataMapper;
    }

    public void setView(@NonNull RutinaDetailView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    private void loadRoutineDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getRoutineDetails();
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);
    }

    private void showRoutineDetailsInView(Routine routine) {
        final RoutineModel routineModel = this.routineModelDataMapper.transform(routine);
        this.viewDetailsView.renderRoutine(routineModel);
    }

    private void getRoutineDetails() {
        this.getRoutineDetailsUseCase.execute(this.routineId, this.routineDetailsCallback);
    }
}
