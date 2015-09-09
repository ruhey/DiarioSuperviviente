package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.medicine.SaveMedicineUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.view.MedicamentoDetailEditView;

import javax.inject.Inject;

public class MedicineDetailEditPresenter implements Presenter{

    private final GetMedicineDetailsUseCase getMedicineDetailsUseCase;
    private final SaveMedicineUseCase saveMedicineUseCase;
    private final MedicineModelDataMapper medicineModelDataMapper;
    private int medicineId;
    private MedicamentoDetailEditView viewDetailsView;
    private final GetMedicineDetailsUseCase.Callback medicineDetailsCallback = new GetMedicineDetailsUseCase.Callback() {
        @Override
        public void onMedicineDataLoaded(Medicine medicine) {
            MedicineDetailEditPresenter.this.showMedicineDetailsInView(medicine);
            MedicineDetailEditPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailEditPresenter.this.hideViewLoading();
            MedicineDetailEditPresenter.this.showErrorMessage(errorBundle);
            MedicineDetailEditPresenter.this.showViewRetry();
        }
    };
    private final SaveMedicineUseCase.Callback medicineSaveCallback = new SaveMedicineUseCase.Callback(){

        @Override
        public void onMedicineDataSaved(Medicine medicine) {
            MedicineDetailEditPresenter.this.hideViewLoading();
            MedicineDetailEditPresenter.this.showOKMessage();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MedicineDetailEditPresenter.this.hideViewLoading();
            MedicineDetailEditPresenter.this.showErrorMessage(errorBundle);
            MedicineDetailEditPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MedicineDetailEditPresenter(GetMedicineDetailsUseCase getMedicineDetailsUseCase,
                                      SaveMedicineUseCase saveMedicineUseCase,
                                        MedicineModelDataMapper medicineModelDataMapper){
        this.getMedicineDetailsUseCase = getMedicineDetailsUseCase;
        this.saveMedicineUseCase = saveMedicineUseCase;
        this.medicineModelDataMapper =medicineModelDataMapper;
    }

    public void setView(@NonNull MedicamentoDetailEditView view) {
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

    public void saveMedicine(Medicine medicine){
        if(medicine.getName() == null ||
                medicine.getFirstDay() == null ||
                medicine.getFirstHour() == null ||
                medicine.getLastDay() == null ||
                medicine.getLastHour() == null ||
                medicine.getInterval() == null ||
                medicine.getDescription() == null) {
            this.viewDetailsView.showError("Los campos no pueden estar vacios.");
        }else {
            this.persistMedicine(medicine);
        }
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

    private void showOKMessage(){
        this.viewDetailsView.showMessage("El medicamento se ha guardado correctamente");
    }

    private void showMedicineDetailsInView(Medicine medicine) {
        final MedicineModel medicineModel = this.medicineModelDataMapper.transform(medicine);
        this.viewDetailsView.renderMedicine(medicineModel);
    }

    private void getMedicineDetails() {
        this.getMedicineDetailsUseCase.execute(this.medicineId, this.medicineDetailsCallback);
    }

    private void persistMedicine(Medicine medicine) {
        this.saveMedicineUseCase.execute(medicine, this.medicineSaveCallback);
    }
}
