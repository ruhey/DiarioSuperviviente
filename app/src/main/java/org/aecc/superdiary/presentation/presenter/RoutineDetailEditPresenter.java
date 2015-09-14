package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.routine.SaveRoutineUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.RoutineModelDataMapper;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.RutinaDetailEditView;

import javax.inject.Inject;

public class RoutineDetailEditPresenter implements Presenter{

    private final GetRoutineDetailsUseCase getRoutineDetailsUseCase;
    private final SaveRoutineUseCase saveRoutineUseCase;
    private final RoutineModelDataMapper routineModelDataMapper;
    private int routineId;
    private RutinaDetailEditView viewDetailsView;
    private final GetRoutineDetailsUseCase.Callback routineDetailsCallback = new GetRoutineDetailsUseCase.Callback() {
        @Override
        public void onRoutineDataLoaded(Routine routine) {
            RoutineDetailEditPresenter.this.showRoutineDetailsInView(routine);
            RoutineDetailEditPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailEditPresenter.this.hideViewLoading();
            RoutineDetailEditPresenter.this.showErrorMessage(errorBundle);
            RoutineDetailEditPresenter.this.showViewRetry();
        }
    };
    private final SaveRoutineUseCase.Callback routineSaveCallback = new SaveRoutineUseCase.Callback(){

        @Override
        public void onRoutineDataSaved(Routine routine) {
            RoutineDetailEditPresenter.this.hideViewLoading();
            RoutineDetailEditPresenter.this.showOKMessage();
            RoutineDetailEditPresenter.this.goBack();

        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineDetailEditPresenter.this.hideViewLoading();
            RoutineDetailEditPresenter.this.showErrorMessage(errorBundle);
            RoutineDetailEditPresenter.this.showViewRetry();
        }
    };

    @Inject
    public RoutineDetailEditPresenter(GetRoutineDetailsUseCase getRoutineDetailsUseCase,
                                      SaveRoutineUseCase saveRoutineUseCase,
                                        RoutineModelDataMapper routineModelDataMapper){
        this.getRoutineDetailsUseCase = getRoutineDetailsUseCase;
        this.saveRoutineUseCase = saveRoutineUseCase;
        this.routineModelDataMapper =routineModelDataMapper;
    }

    public void setView(@NonNull RutinaDetailEditView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
        this.loadRoutineDetails();
    }

    @Override
    public void pause() {
        this.loadRoutineDetails();
    }

    public void initialize(int routineId){
        this.routineId = routineId;
        this.loadRoutineDetails();
    }

    public void editRoutine(int routineId){
        this.viewDetailsView.editRoutine(routineId);
    }

    public void saveRoutine(Routine routine){
        if(routine.getName() == null ||
                routine.getDateRoutine()== null ||
                routine.getPlace() == null ||
                routine.getDateAlarm() == null ||
                routine.getHourAlarm() == null ||
                routine.getDuration() == null ||
                routine.getSatisfaction() == null ||
                routine.getHourRoutine()== null ||
                routine.getDescription()== null ) {
            this.viewDetailsView.showError("Los campos no pueden estar vacios.");
        }else {
            this.persistRoutine(routine);
        }
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

    private void showOKMessage(){
        this.viewDetailsView.showMessage("La rutina se ha guardado correctamente");
    }

    private void showRoutineDetailsInView(Routine routine) {
        final RoutineModel routineModel = this.routineModelDataMapper.transform(routine);
        this.viewDetailsView.renderRoutine(routineModel);
    }

    private void getRoutineDetails() {
        this.getRoutineDetailsUseCase.execute(this.routineId, this.routineDetailsCallback);
    }

    private void persistRoutine(Routine routine) {
        this.saveRoutineUseCase.execute(routine, this.routineSaveCallback);
    }

    private void goBack(){
        this.viewDetailsView.goToDetail();
    }

}
