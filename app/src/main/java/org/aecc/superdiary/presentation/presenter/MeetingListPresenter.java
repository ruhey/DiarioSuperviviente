package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.MeetingModelDataMapper;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.view.CitasListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class MeetingListPresenter implements Presenter {
    private final GetMeetingListUseCase getMeetingListUseCase;
    private final MeetingModelDataMapper meetingModelDataMapper;

    private CitasListView viewListView;

    private final GetMeetingListUseCase.Callback meetingListCallback = new GetMeetingListUseCase.Callback() {
        @Override
        public void onMeetingListLoaded(Collection<Meeting> meetingsCollection) {
            MeetingListPresenter.this.showMeetingsCollectionInView(meetingsCollection);
            MeetingListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingListPresenter.this.hideViewLoading();
            MeetingListPresenter.this.showErrorMessage(errorBundle);
            MeetingListPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MeetingListPresenter(GetMeetingListUseCase getMeetingListUseCase,
                                MeetingModelDataMapper meetingModelDataMapper) {
        this.getMeetingListUseCase = getMeetingListUseCase;
        this.meetingModelDataMapper = meetingModelDataMapper;
    }

    public void setView(@NonNull CitasListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void addMeeting(){
        this.viewListView.addMeetingElement();
    }

    public void initialize() {
        this.loadMeetingList();
    }

    private void loadMeetingList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMeetingList();
    }

    public void onMeetingClicked(MeetingModel meetingModel) {
        this.viewListView.viewMeeting(meetingModel);
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

    private void showMeetingsCollectionInView(Collection<Meeting> meetingsCollection) {
        final Collection<MeetingModel> meetingModelsCollection =
                this.meetingModelDataMapper.transform(meetingsCollection);
        this.viewListView.renderMeetingList(meetingModelsCollection);
    }

    private void getMeetingList() {
        this.getMeetingListUseCase.execute(meetingListCallback);
    }
}
