package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.DeleteMedicineUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.MedicamentoDetailDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class MedicineDetailDeletePresenter implements Presenter {

    private final GetMedicineDetailsUseCase getMedicineDetailsUseCase;
    private final DeleteMedicineUseCase deleteMedicineUseCase;
    private final MedicineModelDataMapper medicineModelDataMapper;
    private int medicineId;
    private MedicamentoDetailDeleteView viewDetailsView;
    private final GetMedicineDetailsUseCase.Callback medicineDetailsCallback = new GetMedicineDetailsUseCase.Callback() {
        @Override
        public void onMedicineDataLoaded(Medicine medicine) {
            MedicineDetailDeletePresenter.this.showMedicineDetailsInView(medicine);
            MedicineDetailDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailDeletePresenter.this.hideViewLoading();
            MedicineDetailDeletePresenter.this.showErrorMessage(errorBundle);
            MedicineDetailDeletePresenter.this.showViewRetry();
        }
    };
    private final DeleteMedicineUseCase.Callback deleteMedicineCallback = new DeleteMedicineUseCase.Callback(){

        @Override
        public void onMedicineDataDeleted(Collection<Medicine> medicinesCollection) {
            MedicineDetailDeletePresenter.this.hideViewLoading();
            MedicineDetailDeletePresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailDeletePresenter.this.hideViewLoading();
            MedicineDetailDeletePresenter.this.showErrorMessage(errorBundle);
            MedicineDetailDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public MedicineDetailDeletePresenter(GetMedicineDetailsUseCase getMedicineDetailsUseCase,
                                        DeleteMedicineUseCase deleteMedicineUseCase,
                                        MedicineModelDataMapper medicineModelDataMapper){
        this.getMedicineDetailsUseCase = getMedicineDetailsUseCase;
        this.deleteMedicineUseCase = deleteMedicineUseCase;
        this.medicineModelDataMapper =medicineModelDataMapper;
    }

    public void setView(@NonNull MedicamentoDetailDeleteView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void initialize(int medicineId){
        this.medicineId = medicineId;
        this.loadMedicineDetails();
    }

    private void loadMedicineDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMedicineDetails();
    }

    public void deleteMedicine(int medicineId){
        this.persistDeletion(medicineId);
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
        this.viewDetailsView.showMessage("El medicamento se ha borrado correctamente");
    }

    private void showMedicineDetailsInView(Medicine medicine) {
        final MedicineModel medicineModel = this.medicineModelDataMapper.transform(medicine);
        this.viewDetailsView.renderMedicine(medicineModel);
    }

    private void getMedicineDetails() {

    }

    private void persistDeletion(int medicineId) {
        this.deleteMedicineUseCase.execute(this.medicineId, this.deleteMedicineCallback);
    }
}
