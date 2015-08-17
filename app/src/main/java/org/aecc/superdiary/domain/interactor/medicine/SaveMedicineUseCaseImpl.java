package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MedicineRepository;

import javax.inject.Inject;


public class SaveMedicineUseCaseImpl implements SaveMedicineUseCase {

    private final MedicineRepository medicineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Medicine medicine = null;
    private Callback callback;
    private final MedicineRepository.MedicineSaveCallback repositoryCallback =
            new MedicineRepository.MedicineSaveCallback() {
                @Override
                public void onMedicineSaved(Medicine medicine) {
                    notifySaveMedicineSuccessfully(medicine);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public SaveMedicineUseCaseImpl(MedicineRepository medicineRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        this.medicineRepository = medicineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Medicine medicine, Callback callback) {
        if (medicine == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.medicine = medicine;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.medicineRepository.saveMedicine(this.medicine, this.repositoryCallback);
    }

    private void notifySaveMedicineSuccessfully(final Medicine medicine) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMedicineDataSaved(medicine);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }

}
