package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.symptom.DeleteSymptomUseCase;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.SymptomModelDataMapper;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.SintomaDetailDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class SymptomDetailDeletePresenter implements Presenter {

    private final GetSymptomDetailsUseCase getSymptomDetailsUseCase;
    private final DeleteSymptomUseCase deleteSymptomUseCase;
    private final SymptomModelDataMapper symptomModelDataMapper;
    private int symptomId;
    private SintomaDetailDeleteView viewDetailsView;
    private final GetSymptomDetailsUseCase.Callback symptomDetailsCallback = new GetSymptomDetailsUseCase.Callback() {
        @Override
        public void onSymptomDataLoaded(Symptom symptom) {
            SymptomDetailDeletePresenter.this.showSymptomDetailsInView(symptom);
            SymptomDetailDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailDeletePresenter.this.hideViewLoading();
            SymptomDetailDeletePresenter.this.showErrorMessage(errorBundle);
            SymptomDetailDeletePresenter.this.showViewRetry();
        }
    };
    private final DeleteSymptomUseCase.Callback deleteSymptomCallback = new DeleteSymptomUseCase.Callback(){

        @Override
        public void onSymptomDataDeleted(Collection<Symptom> symptomsCollection) {
            SymptomDetailDeletePresenter.this.hideViewLoading();
            SymptomDetailDeletePresenter.this.showOKMessage();
            SymptomDetailDeletePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailDeletePresenter.this.hideViewLoading();
            SymptomDetailDeletePresenter.this.showErrorMessage(errorBundle);
            SymptomDetailDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public SymptomDetailDeletePresenter(GetSymptomDetailsUseCase getSymptomDetailsUseCase,
                                        DeleteSymptomUseCase deleteSymptomUseCase,
                                        SymptomModelDataMapper symptomModelDataMapper){
        this.getSymptomDetailsUseCase = getSymptomDetailsUseCase;
        this.deleteSymptomUseCase = deleteSymptomUseCase;
        this.symptomModelDataMapper =symptomModelDataMapper;
    }

    public void setView(@NonNull SintomaDetailDeleteView view) {
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

    private void loadSymptomDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSymptomDetails();
    }

    public void deleteSymptom(int symptomId){
        this.persistDeletion(symptomId);
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
        this.viewDetailsView.showMessage("El síntoma se ha borrado correctamente");
    }

    private void showSymptomDetailsInView(Symptom symptom) {
        final SymptomModel symptomModel = this.symptomModelDataMapper.transform(symptom);
        this.viewDetailsView.renderSymptom(symptomModel);
    }

    private void getSymptomDetails() {
        this.getSymptomDetailsUseCase.execute(this.symptomId, this.symptomDetailsCallback);

    }

    private void persistDeletion(int symptomId) {
        this.deleteSymptomUseCase.execute(this.symptomId, this.deleteSymptomCallback);
    }

    private void goBack() {
        this.viewDetailsView.goToList();
    }
}
