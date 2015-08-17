package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.symptom.*;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteSymptomUseCaseImpl implements org.aecc.superdiary.domain.interactor.symptom.DeleteSymptomUseCase {

    private final SymptomRepository symptomRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int symptomId = -1;
    private Callback callback;
    private final SymptomRepository.SymptomDetionCallback repositoryCallback =
            new SymptomRepository.SymptomDetionCallback() {
                @Override
                public void onSymptomDeleted(Collection<Symptom> symptomsCollection) {
                    notifyDeleteSymptomSuccessfully(symptomsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public DeleteSymptomUseCaseImpl(SymptomRepository symptomRepository, ThreadExecutor threadExecutor,
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
        this.symptomRepository.deleteSymptom(this.symptomId, this.repositoryCallback);
    }

    private void notifyDeleteSymptomSuccessfully(final Collection<Symptom> symptomsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSymptomDataDeleted(symptomsCollection);
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
