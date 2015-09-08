package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.domain.interactor.symptom.SaveSymptomUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.SymptomModelDataMapper;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.SintomaDetailEditView;

import javax.inject.Inject;

public class SymptomDetailEditPresenter implements Presenter{

    private final GetSymptomDetailsUseCase getSymptomDetailsUseCase;
    private final SaveSymptomUseCase saveSymptomUseCase;
    private final SymptomModelDataMapper symptomModelDataMapper;
    private int symptomId;
    private SintomaDetailEditView viewDetailsView;
    private final GetSymptomDetailsUseCase.Callback symptomDetailsCallback = new GetSymptomDetailsUseCase.Callback() {
        @Override
        public void onSymptomDataLoaded(Symptom symptom) {
            SymptomDetailEditPresenter.this.showSymptomDetailsInView(symptom);
            SymptomDetailEditPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailEditPresenter.this.hideViewLoading();
            SymptomDetailEditPresenter.this.showErrorMessage(errorBundle);
            SymptomDetailEditPresenter.this.showViewRetry();
        }
    };
    private final SaveSymptomUseCase.Callback symptomSaveCallback = new SaveSymptomUseCase.Callback(){

        @Override
        public void onSymptomDataSaved(Symptom symptom) {
            SymptomDetailEditPresenter.this.hideViewLoading();
            SymptomDetailEditPresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailEditPresenter.this.hideViewLoading();
            SymptomDetailEditPresenter.this.showErrorMessage(errorBundle);
            SymptomDetailEditPresenter.this.showViewRetry();
        }
    };

    @Inject
    public SymptomDetailEditPresenter(GetSymptomDetailsUseCase getSymptomDetailsUseCase,
                                      SaveSymptomUseCase saveSymptomUseCase,
                                        SymptomModelDataMapper symptomModelDataMapper){
        this.getSymptomDetailsUseCase = getSymptomDetailsUseCase;
        this.saveSymptomUseCase = saveSymptomUseCase;
        this.symptomModelDataMapper =symptomModelDataMapper;
    }

    public void setView(@NonNull SintomaDetailEditView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void initialize(int symptomId){
        this.symptomId = symptomId;
        this.loadSymptomDetails();
    }

    public void saveSymptom(Symptom symptom){
        this.persistSymptom(symptom);
    }

    private void loadSymptomDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSymptomDetails();
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
        this.viewDetailsView.showMessage("El síntoma se ha guardado correctamente");
    }

    private void showSymptomDetailsInView(Symptom symptom) {
        final SymptomModel symptomModel = this.symptomModelDataMapper.transform(symptom);
        this.viewDetailsView.renderSymptom(symptomModel);
    }

    private void getSymptomDetails() {
        this.getSymptomDetailsUseCase.execute(this.symptomId, this.symptomDetailsCallback);
    }

    private void persistSymptom(Symptom symptom) {
        this.saveSymptomUseCase.execute(symptom, this.symptomSaveCallback);
    }
}
