package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.symptom.SaveSymptomUseCase;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import javax.inject.Inject;


public class SaveSymptomUseCaseImpl implements SaveSymptomUseCase {

    private final SymptomRepository symptomRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Symptom symptom = null;
    private Callback callback;
    private final SymptomRepository.SymptomSaveCallback repositoryCallback =
            new SymptomRepository.SymptomSaveCallback() {
                @Override
                public void onSymptomSaved(Symptom symptom) {
                    notifySaveSymptomSuccessfully(symptom);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public SaveSymptomUseCaseImpl(SymptomRepository symptomRepository, ThreadExecutor threadExecutor,
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
        this.symptomRepository.saveSymptom(this.symptom, this.repositoryCallback);
    }

    private void notifySaveSymptomSuccessfully(final Symptom symptom) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSymptomDataSaved(symptom);
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
