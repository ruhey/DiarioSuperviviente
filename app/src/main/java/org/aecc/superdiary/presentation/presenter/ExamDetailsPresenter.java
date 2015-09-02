package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ExamModelDataMapper;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.PruebaDetailView;

import javax.inject.Inject;

public class ExamDetailsPresenter implements Presenter {
    private final GetExamDetailsUseCase getExamDetailsUseCase;
    private final ExamModelDataMapper examModelDataMapper;
    private int examId;
    private PruebaDetailView viewDetailsView;
    private final GetExamDetailsUseCase.Callback examDetailsCallback = new GetExamDetailsUseCase.Callback() {
        @Override
        public void onExamDataLoaded(Exam exam) {
            ExamDetailsPresenter.this.showExamDetailsInView(exam);
            ExamDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailsPresenter.this.hideViewLoading();
            ExamDetailsPresenter.this.showErrorMessage(errorBundle);
            ExamDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public ExamDetailsPresenter(GetExamDetailsUseCase getExamDetailsUseCase,
                                   ExamModelDataMapper examModelDataMapper) {
        this.getExamDetailsUseCase = getExamDetailsUseCase;
        this.examModelDataMapper = examModelDataMapper;
    }

    public void setView(@NonNull PruebaDetailView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    private void loadExamDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getExamDetails();
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);
    }

    private void showExamDetailsInView(Exam exam) {
        final ExamModel examModel = this.examModelDataMapper.transform(exam);
        this.viewDetailsView.renderExam(examModel);
    }

    private void getExamDetails() {
        this.getExamDetailsUseCase.execute(this.examId, this.examDetailsCallback);
    }
}
