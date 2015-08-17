package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.exam.*;
import org.aecc.superdiary.domain.repository.ExamRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteExamUseCaseImpl implements org.aecc.superdiary.domain.interactor.exam.DeleteExamUseCase {

    private final ExamRepository examRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int examId = -1;
    private Callback callback;
    private final ExamRepository.ExamDetionCallback repositoryCallback =
            new ExamRepository.ExamDetionCallback() {
                @Override
                public void onExamDeleted(Collection<Exam> examsCollection) {
                    notifyDeleteExamSuccessfully(examsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public DeleteExamUseCaseImpl(ExamRepository examRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.examRepository = examRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int examId, Callback callback) {
        if (examId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.examId = examId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.examRepository.deleteExam(this.examId, this.repositoryCallback);
    }

    private void notifyDeleteExamSuccessfully(final Collection<Exam> examsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onExamDataDeleted(examsCollection);
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
