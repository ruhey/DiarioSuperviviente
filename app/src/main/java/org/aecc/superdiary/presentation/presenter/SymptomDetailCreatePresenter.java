package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.symptom.CreateSymptomUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.SymptomModelDataMapper;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.SintomaDetailCreateView;
import org.aecc.superdiary.presentation.view.SintomaDetailEditView;

import javax.inject.Inject;

public class SymptomDetailCreatePresenter implements Presenter{


    private final CreateSymptomUseCase createSymptomUseCase;
    private final SymptomModelDataMapper symptomModelDataMapper;
    private int symptomId;
    private SintomaDetailCreateView viewDetailsView;

    private final CreateSymptomUseCase.Callback createSymptomCallback = new CreateSymptomUseCase.Callback(){

        @Override
        public void onSymptomDataCreated(Symptom symptom) {
            SymptomDetailCreatePresenter.this.hideViewLoading();
            SymptomDetailCreatePresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailCreatePresenter.this.hideViewLoading();
            SymptomDetailCreatePresenter.this.showErrorMessage(errorBundle);
            SymptomDetailCreatePresenter.this.showViewRetry();
        }
    };

    @Inject
    public SymptomDetailCreatePresenter(
                                        CreateSymptomUseCase createSymptomUseCase,
                                        SymptomModelDataMapper symptomModelDataMapper){

        this.createSymptomUseCase = createSymptomUseCase;
        this.symptomModelDataMapper =symptomModelDataMapper;
    }

    public void setView(@NonNull SintomaDetailCreateView view) {
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

    public void createSymptom(Symptom symptom){
        this.persistCreation(symptom);
    }

    private void loadSymptomDetails() {
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
        this.viewDetailsView.showMessage("El síntoma se ha creado correctamente");
    }

    private void persistCreation(Symptom symptom){
        this.createSymptomUseCase.execute(symptom, this.createSymptomCallback);
    }
}
