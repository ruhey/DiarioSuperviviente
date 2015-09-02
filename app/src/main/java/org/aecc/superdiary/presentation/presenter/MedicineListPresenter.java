package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.MedicamentosListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class MedicineListPresenter implements Presenter {
    private final GetMedicineListUseCase getMedicineListUseCase;
    private final MedicineModelDataMapper medicineModelDataMapper;

    private MedicamentosListView viewListView;

    private final GetMedicineListUseCase.Callback medicineListCallback = new GetMedicineListUseCase.Callback() {
        @Override
        public void onMedicineListLoaded(Collection<Medicine> medicinesCollection) {
            MedicineListPresenter.this.showMedicinesCollectionInView(medicinesCollection);
            MedicineListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineListPresenter.this.hideViewLoading();
            MedicineListPresenter.this.showErrorMessage(errorBundle);
            MedicineListPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MedicineListPresenter(GetMedicineListUseCase getMedicineListUseCase,
                                MedicineModelDataMapper medicineModelDataMapper) {
        this.getMedicineListUseCase = getMedicineListUseCase;
        this.medicineModelDataMapper = medicineModelDataMapper;
    }

    public void setView(@NonNull MedicamentosListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void initialize() {
        this.loadMedicineList();
    }

    private void loadMedicineList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMedicineList();
    }

    public void onMedicineClicked(MedicineModel medicineModel) {
        this.viewListView.viewMedicine(medicineModel);
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

    private void showMedicinesCollectionInView(Collection<Medicine> medicinesCollection) {
        final Collection<MedicineModel> medicineModelsCollection =
                this.medicineModelDataMapper.transform(medicinesCollection);
        this.viewListView.renderMedicineList(medicineModelsCollection);
    }

    private void getMedicineList() {
        this.getMedicineListUseCase.execute(medicineListCallback);
    }
}
