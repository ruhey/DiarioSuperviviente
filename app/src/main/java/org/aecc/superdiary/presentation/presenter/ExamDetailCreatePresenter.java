package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.exam.CreateExamUseCase;
import org.aecc.superdiary.domain.interactor.exam.GetExamDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ExamModelDataMapper;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.PruebaDetailCreateView;
import org.aecc.superdiary.presentation.view.PruebaDetailEditView;

import javax.inject.Inject;

public class ExamDetailCreatePresenter implements Presenter{


    private final CreateExamUseCase createExamUseCase;
    private final ExamModelDataMapper examModelDataMapper;
    private int examId;
    private PruebaDetailCreateView viewDetailsView;

    private final CreateExamUseCase.Callback createExamCallback = new CreateExamUseCase.Callback(){

        @Override
        public void onExamDataCreated(Exam exam) {
            ExamDetailCreatePresenter.this.hideViewLoading();
            ExamDetailCreatePresenter.this.showOKMessage();
            ExamDetailCreatePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamDetailCreatePresenter.this.hideViewLoading();
            ExamDetailCreatePresenter.this.showErrorMessage(errorBundle);
            ExamDetailCreatePresenter.this.showViewRetry();
        }
    };

    @Inject
    public ExamDetailCreatePresenter(
                                        CreateExamUseCase createExamUseCase,
                                        ExamModelDataMapper examModelDataMapper){

        this.createExamUseCase = createExamUseCase;
        this.examModelDataMapper =examModelDataMapper;
    }

    public void setView(@NonNull PruebaDetailCreateView view) {
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

    public void createExam(Exam exam){
        if(exam.getName() == null ||
                exam.getDateExam()== null ||
                exam.getHourExam()== null ||
                exam.getDescription()== null ) {
            this.viewDetailsView.showError("Los campos no pueden estar vacios.");
        }else {
            this.persistCreation(exam);
        }
    }

    private void loadExamDetails() {
        this.hideViewRetry();
        this.showViewLoading();

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
        this.viewDetailsView.showMessage("La prueba se ha creado correctamente");
    }

    private void persistCreation(Exam exam){
        this.createExamUseCase.execute(exam, this.createExamCallback);
    }

    private void goBack(){
        this.viewDetailsView.goToList();
    }
}
