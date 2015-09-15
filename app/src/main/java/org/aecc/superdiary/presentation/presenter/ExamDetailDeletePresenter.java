package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.exam.DeleteExamUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ExamModelDataMapper;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.PruebaDetailDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class ExamDetailDeletePresenter implements Presenter {

    private final GetExamDetailsUseCase getExamDetailsUseCase;
    private final DeleteExamUseCase deleteExamUseCase;
    private final ExamModelDataMapper examModelDataMapper;
    private int examId;
    private PruebaDetailDeleteView viewDetailsView;
    private final GetExamDetailsUseCase.Callback examDetailsCallback = new GetExamDetailsUseCase.Callback() {
        @Override
        public void onExamDataLoaded(Exam exam) {
            ExamDetailDeletePresenter.this.showExamDetailsInView(exam);
            ExamDetailDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailDeletePresenter.this.hideViewLoading();
            ExamDetailDeletePresenter.this.showErrorMessage(errorBundle);
            ExamDetailDeletePresenter.this.showViewRetry();
        }
    };
    private final DeleteExamUseCase.Callback deleteExamCallback = new DeleteExamUseCase.Callback(){

        @Override
        public void onExamDataDeleted(Collection<Exam> examsCollection) {
            ExamDetailDeletePresenter.this.hideViewLoading();
            ExamDetailDeletePresenter.this.showOKMessage();
            ExamDetailDeletePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailDeletePresenter.this.hideViewLoading();
            ExamDetailDeletePresenter.this.showErrorMessage(errorBundle);
            ExamDetailDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public ExamDetailDeletePresenter(GetExamDetailsUseCase getExamDetailsUseCase,
                                        DeleteExamUseCase deleteExamUseCase,
                                        ExamModelDataMapper examModelDataMapper){
        this.getExamDetailsUseCase = getExamDetailsUseCase;
        this.deleteExamUseCase = deleteExamUseCase;
        this.examModelDataMapper =examModelDataMapper;
    }

    public void setView(@NonNull PruebaDetailDeleteView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public void initialize(int examId){
        this.examId = examId;
        this.loadExamDetails();
    }

    private void loadExamDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getExamDetails();
    }

    public void deleteExam(int examId){
        this.persistDeletion(examId);
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
    private void showOKMessage(){
        this.viewDetailsView.showMessage("La prueba se ha borrado correctamente");
    }

    private void showExamDetailsInView(Exam exam) {
        final ExamModel examModel = this.examModelDataMapper.transform(exam);
        this.viewDetailsView.renderExam(examModel);
    }

    private void getExamDetails() {
        this.getExamDetailsUseCase.execute(this.examId, this.examDetailsCallback);
    }

    private void persistDeletion(int examId) {
        this.deleteExamUseCase.execute(this.examId, this.deleteExamCallback);
    }

    private void goBack(){
        this.viewDetailsView.goToList();
    }
}
