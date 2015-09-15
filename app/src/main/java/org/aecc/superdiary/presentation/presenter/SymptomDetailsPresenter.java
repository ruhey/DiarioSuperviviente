package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.SymptomModelDataMapper;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.SintomaDetailView;

import javax.inject.Inject;

public class SymptomDetailsPresenter implements Presenter {
    private final GetSymptomDetailsUseCase getSymptomDetailsUseCase;
    private final SymptomModelDataMapper symptomModelDataMapper;
    private int symptomId;
    private SintomaDetailView viewDetailsView;
    private final GetSymptomDetailsUseCase.Callback symptomDetailsCallback = new GetSymptomDetailsUseCase.Callback() {
        @Override
        public void onSymptomDataLoaded(Symptom symptom) {
            SymptomDetailsPresenter.this.showSymptomDetailsInView(symptom);
            SymptomDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomDetailsPresenter.this.hideViewLoading();
            SymptomDetailsPresenter.this.showErrorMessage(errorBundle);
            SymptomDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public SymptomDetailsPresenter(GetSymptomDetailsUseCase getSymptomDetailsUseCase,
                                   SymptomModelDataMapper symptomModelDataMapper) {
        this.getSymptomDetailsUseCase = getSymptomDetailsUseCase;
        this.symptomModelDataMapper = symptomModelDataMapper;
    }

    public void setView(@NonNull SintomaDetailView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
        this.getSymptomDetails();
    }

    @Override
    public void pause() {
    }

    public void initialize(int symptomId){
        this.symptomId = symptomId;
        this.loadSymptomDetails();
    }

    public void editSymptom(int symptomId){this.viewDetailsView.editSymptom(symptomId);}

    public void deleteSymptom(int symptomId){this.viewDetailsView.deleteSymptom(symptomId);}



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

    private void showSymptomDetailsInView(Symptom symptom) {
        final SymptomModel symptomModel = this.symptomModelDataMapper.transform(symptom);
        this.viewDetailsView.renderSymptom(symptomModel);
    }

    private void getSymptomDetails() {
        this.getSymptomDetailsUseCase.execute(this.symptomId, this.symptomDetailsCallback);
    }
}
