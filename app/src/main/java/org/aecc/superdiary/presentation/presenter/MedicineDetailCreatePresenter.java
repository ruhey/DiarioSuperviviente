package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.CreateMedicineUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.MedicamentoDetailCreateView;
import org.aecc.superdiary.presentation.view.MedicamentoDetailEditView;

import javax.inject.Inject;

public class MedicineDetailCreatePresenter implements Presenter{


    private final CreateMedicineUseCase createMedicineUseCase;
    private final MedicineModelDataMapper medicineModelDataMapper;
    private int medicineId;
    private MedicamentoDetailCreateView viewDetailsView;

    private final CreateMedicineUseCase.Callback createMedicineCallback = new CreateMedicineUseCase.Callback(){

        @Override
        public void onMedicineDataCreated(Medicine medicine) {
            MedicineDetailCreatePresenter.this.hideViewLoading();
            MedicineDetailCreatePresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailCreatePresenter.this.hideViewLoading();
            MedicineDetailCreatePresenter.this.showErrorMessage(errorBundle);
            MedicineDetailCreatePresenter.this.showViewRetry();
        }
    };

    @Inject
    public MedicineDetailCreatePresenter(
                                        CreateMedicineUseCase createMedicineUseCase,
                                        MedicineModelDataMapper medicineModelDataMapper){

        this.createMedicineUseCase = createMedicineUseCase;
        this.medicineModelDataMapper =medicineModelDataMapper;
    }

    public void setView(@NonNull MedicamentoDetailCreateView view) {
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

    public void createMedicine(Medicine medicine){
        this.persistCreation(medicine);
    }

    private void loadMedicineDetails() {
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
        this.viewDetailsView.showMessage("El medicamento se ha creado correctamente");
    }

    private void persistCreation(Medicine medicine){
        this.createMedicineUseCase.execute(medicine, this.createMedicineCallback);
    }
}
