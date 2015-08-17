package org.aecc.superdiary.domain.interactor.exam;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.exam.SaveExamUseCase;
import org.aecc.superdiary.domain.repository.ExamRepository;

import javax.inject.Inject;


public class SaveExamUseCaseImpl implements SaveExamUseCase {

    private final ExamRepository examRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Exam exam = null;
    private Callback callback;
    private final ExamRepository.ExamSaveCallback repositoryCallback =
            new ExamRepository.ExamSaveCallback() {
                @Override
                public void onExamSaved(Exam exam) {
                    notifySaveExamSuccessfully(exam);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public SaveExamUseCaseImpl(ExamRepository examRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        this.examRepository = examRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Exam exam, Callback callback) {
        if (exam == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.exam = exam;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.examRepository.saveExam(this.exam, this.repositoryCallback);
    }

    private void notifySaveExamSuccessfully(final Exam exam) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onExamDataSaved(exam);
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
