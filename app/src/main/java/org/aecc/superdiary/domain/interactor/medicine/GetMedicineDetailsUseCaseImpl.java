package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MedicineRepository;

import javax.inject.Inject;

public class GetMedicineDetailsUseCaseImpl implements GetMedicineDetailsUseCase {

    private final MedicineRepository medicineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int medicineId = -1;
    private Callback callback;
    private final MedicineRepository.MedicineDetailsCallback repositoryCallback =
            new MedicineRepository.MedicineDetailsCallback() {
                @Override
                public void onMedicineLoaded(Medicine medicine) {
                    notifyGetMedicineDetailsSuccessfully(medicine);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetMedicineDetailsUseCaseImpl(MedicineRepository medicineRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.medicineRepository = medicineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int medicineId, Callback callback) {
        if (medicineId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.medicineId = medicineId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.medicineRepository.getMedicineById(this.medicineId, this.repositoryCallback);
    }

    private void notifyGetMedicineDetailsSuccessfully(final Medicine medicine) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMedicineDataLoaded(medicine);
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
