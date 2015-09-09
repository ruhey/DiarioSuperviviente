package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.RoutineModelDataMapper;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.view.RutinasListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class RoutineListPresenter implements Presenter {
    private final GetRoutineListUseCase getRoutineListUseCase;
    private final RoutineModelDataMapper routineModelDataMapper;

    private RutinasListView viewListView;

    private final GetRoutineListUseCase.Callback routineListCallback = new GetRoutineListUseCase.Callback() {
        @Override
        public void onRoutineListLoaded(Collection<Routine> routinesCollection) {
            RoutineListPresenter.this.showRoutinesCollectionInView(routinesCollection);
            RoutineListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            RoutineListPresenter.this.hideViewLoading();
            RoutineListPresenter.this.showErrorMessage(errorBundle);
            RoutineListPresenter.this.showViewRetry();
        }
    };

    @Inject
    public RoutineListPresenter(GetRoutineListUseCase getRoutineListUseCase,
                                RoutineModelDataMapper routineModelDataMapper) {
        this.getRoutineListUseCase = getRoutineListUseCase;
        this.routineModelDataMapper = routineModelDataMapper;
    }

    public void setView(@NonNull RutinasListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void initialize() {
        this.loadRoutineList();
    }

    private void loadRoutineList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getRoutineList();
    }

    public void onRoutineClicked(RoutineModel routineModel) {
        this.viewListView.viewRoutine(routineModel);
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

    public void add(){
        this.viewListView.addRoutine();
    }

    private void showRoutinesCollectionInView(Collection<Routine> routinesCollection) {
        final Collection<RoutineModel> routineModelsCollection =
                this.routineModelDataMapper.transform(routinesCollection);
        this.viewListView.renderRoutineList(routineModelsCollection);
    }

    private void getRoutineList() {
        this.getRoutineListUseCase.execute(routineListCallback);
    }
}
