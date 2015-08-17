package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MedicineRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteMedicineUseCaseImpl implements DeleteMedicineUseCase {

    private final MedicineRepository medicineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int medicineId = -1;
    private Callback callback;
    private final MedicineRepository.MedicineDetionCallback repositoryCallback =
            new MedicineRepository.MedicineDetionCallback() {
                @Override
                public void onMedicineDeleted(Collection<Medicine> medicinesCollection) {
                    notifyDeleteMedicineSuccessfully(medicinesCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public DeleteMedicineUseCaseImpl(MedicineRepository medicineRepository, ThreadExecutor threadExecutor,
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
        this.medicineRepository.deleteMedicine(this.medicineId, this.repositoryCallback);
    }

    private void notifyDeleteMedicineSuccessfully(final Collection<Medicine> medicinesCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMedicineDataDeleted(medicinesCollection);
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
