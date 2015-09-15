package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.routine.DeleteRoutineUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.RoutineModelDataMapper;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.RutinaDetailDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class RoutineDetailDeletePresenter implements Presenter {

    private final GetRoutineDetailsUseCase getRoutineDetailsUseCase;
    private final DeleteRoutineUseCase deleteRoutineUseCase;
    private final RoutineModelDataMapper routineModelDataMapper;
    private int routineId;
    private RutinaDetailDeleteView viewDetailsView;
    private final GetRoutineDetailsUseCase.Callback routineDetailsCallback = new GetRoutineDetailsUseCase.Callback() {
        @Override
        public void onRoutineDataLoaded(Routine routine) {
            RoutineDetailDeletePresenter.this.showRoutineDetailsInView(routine);
            RoutineDetailDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailDeletePresenter.this.hideViewLoading();
            RoutineDetailDeletePresenter.this.showErrorMessage(errorBundle);
            RoutineDetailDeletePresenter.this.showViewRetry();
        }
    };
    private final DeleteRoutineUseCase.Callback deleteRoutineCallback = new DeleteRoutineUseCase.Callback(){

        @Override
        public void onRoutineDataDeleted(Collection<Routine> routinesCollection) {
            RoutineDetailDeletePresenter.this.hideViewLoading();
            RoutineDetailDeletePresenter.this.showOKMessage();
            RoutineDetailDeletePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailDeletePresenter.this.hideViewLoading();
            RoutineDetailDeletePresenter.this.showErrorMessage(errorBundle);
            RoutineDetailDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public RoutineDetailDeletePresenter(GetRoutineDetailsUseCase getRoutineDetailsUseCase,
                                        DeleteRoutineUseCase deleteRoutineUseCase,
                                        RoutineModelDataMapper routineModelDataMapper){
        this.getRoutineDetailsUseCase = getRoutineDetailsUseCase;
        this.deleteRoutineUseCase = deleteRoutineUseCase;
        this.routineModelDataMapper =routineModelDataMapper;
    }

    public void setView(@NonNull RutinaDetailDeleteView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void initialize(int routineId){
        this.routineId = routineId;
        this.loadRoutineDetails();
    }

    private void loadRoutineDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getRoutineDetails();
    }

    public void deleteRoutine(int routineId){
        this.persistDeletion(routineId);
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
    private void showOKMessage(){
        this.viewDetailsView.showMessage("El contacto se ha borrado correctamente");
    }

    private void showRoutineDetailsInView(Routine routine) {
        final RoutineModel routineModel = this.routineModelDataMapper.transform(routine);
        this.viewDetailsView.renderRoutine(routineModel);
    }

    private void getRoutineDetails() {

    }

    private void persistDeletion(int routineId) {
        this.deleteRoutineUseCase.execute(this.routineId, this.deleteRoutineCallback);
    }

    private void goBack() {
        this.viewDetailsView.goToList();
    }
}
