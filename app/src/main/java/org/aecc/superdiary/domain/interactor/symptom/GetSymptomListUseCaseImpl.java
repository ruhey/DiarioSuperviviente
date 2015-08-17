package org.aecc.superdiary.domain.interactor.symptom;

import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.symptom.*;
import org.aecc.superdiary.domain.repository.SymptomRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetSymptomListUseCaseImpl implements GetSymptomListUseCase {

    private final SymptomRepository symptomRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private final SymptomRepository.SymptomListCallback repositoryCallback =
            new SymptomRepository.SymptomListCallback() {
                @Override
                public void onSymptomListLoaded(Collection<Symptom> symptomsCollection) {
                    notifyGetSymptomListSuccessfully(symptomsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetSymptomListUseCaseImpl(SymptomRepository symptomRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.symptomRepository = symptomRepository;
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
        this.symptomRepository.getSymptomList(this.repositoryCallback);
    }

    private void notifyGetSymptomListSuccessfully(final Collection<Symptom> symptomsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSymptomListLoaded(symptomsCollection);
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
