package org.aecc.superdiary.domain.interactor.symptom;


import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.symptom.*;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import javax.inject.Inject;

public class CreateSymptomUseCaseImpl implements org.aecc.superdiary.domain.interactor.symptom.CreateSymptomUseCase {

    private final SymptomRepository symptomRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Symptom symptom = null;
    private Callback callback;
    private final SymptomRepository.SymptomCreationCallback repositoryCallback =
            new SymptomRepository.SymptomCreationCallback() {
                @Override
                public void onSymptomCreated(Symptom symptom) {
                    notifyCreateSymptomSuccessfully(symptom);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public CreateSymptomUseCaseImpl(SymptomRepository symptomRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.symptomRepository = symptomRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Symptom symptom, Callback callback) {
        if (symptom == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.symptom = symptom;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.symptomRepository.createSymptom(this.symptom, this.repositoryCallback);
    }

    private void notifyCreateSymptomSuccessfully(final Symptom symptom) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSymptomDataCreated(symptom);
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
