package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.exam.GetExamListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.ExamModelDataMapper;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.PruebasListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class ExamListPresenter implements Presenter {
    private final GetExamListUseCase getExamListUseCase;
    private final ExamModelDataMapper examModelDataMapper;

    private PruebasListView viewListView;

    private final GetExamListUseCase.Callback examListCallback = new GetExamListUseCase.Callback() {
        @Override
        public void onExamListLoaded(Collection<Exam> examsCollection) {
            ExamListPresenter.this.showExamsCollectionInView(examsCollection);
            ExamListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ExamListPresenter.this.hideViewLoading();
            ExamListPresenter.this.showErrorMessage(errorBundle);
            ExamListPresenter.this.showViewRetry();
        }
    };

    @Inject
    public ExamListPresenter(GetExamListUseCase getExamListUseCase,
                                ExamModelDataMapper examModelDataMapper) {
        this.getExamListUseCase = getExamListUseCase;
        this.examModelDataMapper = examModelDataMapper;
    }

    public void setView(@NonNull PruebasListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void initialize() {
        this.loadExamList();
    }

    private void loadExamList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getExamList();
    }

    public void onExamClicked(ExamModel examModel) {
        this.viewListView.viewExam(examModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showExamsCollectionInView(Collection<Exam> examsCollection) {
        final Collection<ExamModel> examModelsCollection =
                this.examModelDataMapper.transform(examsCollection);
        this.viewListView.renderExamList(examModelsCollection);
    }

    private void getExamList() {
        this.getExamListUseCase.execute(examListCallback);
    }
}
