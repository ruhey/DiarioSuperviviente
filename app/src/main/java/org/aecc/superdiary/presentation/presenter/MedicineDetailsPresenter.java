package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.MedicamentoDetailView;

import javax.inject.Inject;

public class MedicineDetailsPresenter implements Presenter {

    private final GetMedicineDetailsUseCase getMedicineDetailsUseCase;
    private final MedicineModelDataMapper medicineModelDataMapper;
    private int medicineId;
    private MedicamentoDetailView viewDetailsView;
    private final GetMedicineDetailsUseCase.Callback medicineDetailsCallback = new GetMedicineDetailsUseCase.Callback() {
        @Override
        public void onMedicineDataLoaded(Medicine medicine) {
            MedicineDetailsPresenter.this.showMedicineDetailsInView(medicine);
            MedicineDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailsPresenter.this.hideViewLoading();
            MedicineDetailsPresenter.this.showErrorMessage(errorBundle);
            MedicineDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MedicineDetailsPresenter(GetMedicineDetailsUseCase getMedicineDetailsUseCase,
                                MedicineModelDataMapper medicineModelDataMapper) {
        this.getMedicineDetailsUseCase = getMedicineDetailsUseCase;
        this.medicineModelDataMapper = medicineModelDataMapper;
    }

    public void setView(@NonNull MedicamentoDetailView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    private void loadMedicineDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMedicineDetails();
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

    private void showMedicineDetailsInView(Medicine medicine) {
        final MedicineModel medicineModel = this.medicineModelDataMapper.transform(medicine);
        this.viewDetailsView.renderMedicine(medicineModel);
    }

    private void getMedicineDetails() {
        this.getMedicineDetailsUseCase.execute(this.medicineId, this.medicineDetailsCallback);
    }
}
