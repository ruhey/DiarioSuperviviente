package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.exam.*;
import org.aecc.superdiary.domain.repository.ExamRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetExamListUseCaseImpl implements org.aecc.superdiary.domain.interactor.exam.GetExamListUseCase {

    private final ExamRepository examRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private final ExamRepository.ExamListCallback repositoryCallback =
            new ExamRepository.ExamListCallback() {
                @Override
                public void onExamListLoaded(Collection<Exam> examsCollection) {
                    notifyGetExamListSuccessfully(examsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetExamListUseCaseImpl(ExamRepository examRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.examRepository = examRepository;
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
        this.examRepository.getExamList(this.repositoryCallback);
    }

    private void notifyGetExamListSuccessfully(final Collection<Exam> examsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onExamListLoaded(examsCollection);
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
