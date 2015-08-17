package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.symptom.*;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import javax.inject.Inject;

public class GetSymptomDetailsUseCaseImpl implements org.aecc.superdiary.domain.interactor.symptom.GetSymptomDetailsUseCase {

    private final SymptomRepository symptomRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int symptomId = -1;
    private Callback callback;
    private final SymptomRepository.SymptomDetailsCallback repositoryCallback =
            new SymptomRepository.SymptomDetailsCallback() {
                @Override
                public void onSymptomLoaded(Symptom symptom) {
                    notifyGetSymptomDetailsSuccessfully(symptom);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetSymptomDetailsUseCaseImpl(SymptomRepository symptomRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.symptomRepository = symptomRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int symptomId, Callback callback) {
        if (symptomId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.symptomId = symptomId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.symptomRepository.getSymptomById(this.symptomId, this.repositoryCallback);
    }

    private void notifyGetSymptomDetailsSuccessfully(final Symptom symptom) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSymptomDataLoaded(symptom);
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
