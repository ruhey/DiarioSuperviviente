package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.symptom.GetSymptomListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.SymptomModelDataMapper;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.SintomasListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class SymptomListPresenter implements Presenter {
    private final GetSymptomListUseCase getSymptomListUseCase;
    private final SymptomModelDataMapper symptomModelDataMapper;

    private SintomasListView viewListView;

    private final GetSymptomListUseCase.Callback symptomListCallback = new GetSymptomListUseCase.Callback() {
        @Override
        public void onSymptomListLoaded(Collection<Symptom> symptomsCollection) {
            SymptomListPresenter.this.showSymptomsCollectionInView(symptomsCollection);
            SymptomListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            SymptomListPresenter.this.hideViewLoading();
            SymptomListPresenter.this.showErrorMessage(errorBundle);
            SymptomListPresenter.this.showViewRetry();
        }
    };

    @Inject
    public SymptomListPresenter(GetSymptomListUseCase getSymptomListUseCase,
                                SymptomModelDataMapper symptomModelDataMapper) {
        this.getSymptomListUseCase = getSymptomListUseCase;
        this.symptomModelDataMapper = symptomModelDataMapper;
    }

    public void setView(@NonNull SintomasListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void initialize() {
        this.loadSymptomList();
    }

    private void loadSymptomList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getSymptomList();
    }

    public void onSymptomClicked(SymptomModel symptomModel) {
        this.viewListView.viewSymptom(symptomModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showSymptomsCollectionInView(Collection<Symptom> symptomsCollection) {
        final Collection<SymptomModel> symptomModelsCollection =
                this.symptomModelDataMapper.transform(symptomsCollection);
        this.viewListView.renderSymptomList(symptomModelsCollection);
    }

    private void getSymptomList() {
        this.getSymptomListUseCase.execute(symptomListCallback);
    }

    public void add(){
        this.viewListView.addSymptom();
    }
}
