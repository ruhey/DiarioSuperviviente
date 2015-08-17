package org.aecc.superdiary.domain.interactor.medicine;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MedicineRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetMedicineListUseCaseImpl implements GetMedicineListUseCase {

    private final MedicineRepository medicineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private final MedicineRepository.MedicineListCallback repositoryCallback =
            new MedicineRepository.MedicineListCallback() {
                @Override
                public void onMedicineListLoaded(Collection<Medicine> medicinesCollection) {
                    notifyGetMedicineListSuccessfully(medicinesCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetMedicineListUseCaseImpl(MedicineRepository medicineRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.medicineRepository = medicineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.medicineRepository.getMedicineList(this.repositoryCallback);
    }

    private void notifyGetMedicineListSuccessfully(final Collection<Medicine> medicinesCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMedicineListLoaded(medicinesCollection);
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
