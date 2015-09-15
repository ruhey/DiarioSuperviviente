package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.domain.interactor.exam.SaveExamUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ExamModelDataMapper;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.PruebaDetailEditView;

import javax.inject.Inject;

public class ExamDetailEditPresenter implements Presenter{

    private final GetExamDetailsUseCase getExamDetailsUseCase;
    private final SaveExamUseCase saveExamUseCase;
    private final ExamModelDataMapper examModelDataMapper;
    private int examId;
    private PruebaDetailEditView viewDetailsView;
    private final GetExamDetailsUseCase.Callback examDetailsCallback = new GetExamDetailsUseCase.Callback() {
        @Override
        public void onExamDataLoaded(Exam exam) {
            ExamDetailEditPresenter.this.showExamDetailsInView(exam);
            ExamDetailEditPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailEditPresenter.this.hideViewLoading();
            ExamDetailEditPresenter.this.showErrorMessage(errorBundle);
            ExamDetailEditPresenter.this.showViewRetry();
        }
    };
    private final SaveExamUseCase.Callback examSaveCallback = new SaveExamUseCase.Callback(){

        @Override
        public void onExamDataSaved(Exam exam) {
            ExamDetailEditPresenter.this.hideViewLoading();
            ExamDetailEditPresenter.this.showOKMessage();
            ExamDetailEditPresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailEditPresenter.this.hideViewLoading();
            ExamDetailEditPresenter.this.showErrorMessage(errorBundle);
            ExamDetailEditPresenter.this.showViewRetry();
        }
    };

    @Inject
    public ExamDetailEditPresenter(GetExamDetailsUseCase getExamDetailsUseCase,
                                      SaveExamUseCase saveExamUseCase,
                                        ExamModelDataMapper examModelDataMapper){
        this.getExamDetailsUseCase = getExamDetailsUseCase;
        this.saveExamUseCase = saveExamUseCase;
        this.examModelDataMapper =examModelDataMapper;
    }

    public void setView(@NonNull PruebaDetailEditView view) {
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

    public void editExam(int examId){
        this.viewDetailsView.editExam(examId);
    }

    public void saveExam(Exam exam){
        if(exam.getName() == null ||
                exam.getDateExam()== null ||
                exam.getHourExam()== null ||
                exam.getDescription()== null ) {
            this.viewDetailsView.showError("Los campos no pueden estar vacios.");
        }else {
            this.persistExam(exam);
        }
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

    private void showOKMessage(){
        this.viewDetailsView.showMessage("La prueba se ha guardado correctamente");
    }

    private void showExamDetailsInView(Exam exam) {
        final ExamModel examModel = this.examModelDataMapper.transform(exam);
        this.viewDetailsView.renderExam(examModel);
    }

    private void getExamDetails() {
        this.getExamDetailsUseCase.execute(this.examId, this.examDetailsCallback);
    }

    private void persistExam(Exam exam) {
        this.saveExamUseCase.execute(exam, this.examSaveCallback);
    }

    private void goBack() {
        this.viewDetailsView.goBack();
    }
}
