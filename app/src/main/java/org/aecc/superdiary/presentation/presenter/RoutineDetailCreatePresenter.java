package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.routine.CreateRoutineUseCase;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.RoutineModelDataMapper;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.RutinaDetailCreateView;
import org.aecc.superdiary.presentation.view.RutinaDetailEditView;

import javax.inject.Inject;

public class RoutineDetailCreatePresenter implements Presenter{


    private final CreateRoutineUseCase createRoutineUseCase;
    private final RoutineModelDataMapper routineModelDataMapper;
    private int routineId;
    private RutinaDetailCreateView viewDetailsView;

    private final CreateRoutineUseCase.Callback createRoutineCallback = new CreateRoutineUseCase.Callback(){

        @Override
        public void onRoutineDataCreated(Routine routine) {
            RoutineDetailCreatePresenter.this.hideViewLoading();
            RoutineDetailCreatePresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailCreatePresenter.this.hideViewLoading();
            RoutineDetailCreatePresenter.this.showErrorMessage(errorBundle);
            RoutineDetailCreatePresenter.this.showViewRetry();
        }
    };

    @Inject
    public RoutineDetailCreatePresenter(
                                        CreateRoutineUseCase createRoutineUseCase,
                                        RoutineModelDataMapper routineModelDataMapper){

        this.createRoutineUseCase = createRoutineUseCase;
        this.routineModelDataMapper =routineModelDataMapper;
    }

    public void setView(@NonNull RutinaDetailCreateView view) {
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

    public void createRoutine(Routine routine){
        this.persistCreation(routine);
    }

    private void loadRoutineDetails() {
        this.hideViewRetry();
        this.showViewLoading();

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
        this.viewDetailsView.showMessage("El contacto se ha creado correctamente");
    }

    private void persistCreation(Routine routine){
        this.createRoutineUseCase.execute(routine, this.createRoutineCallback);
    }
}
