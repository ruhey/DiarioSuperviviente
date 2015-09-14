package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MeetingModelDataMapper;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.view.CitaDetailView;

import javax.inject.Inject;

public class MeetingDetailsPresenter implements Presenter {
    private final GetMeetingDetailsUseCase getMeetingDetailsUseCase;
    private final MeetingModelDataMapper meetingModelDataMapper;
    private int meetingId;
    private CitaDetailView viewDetailsView;
    private final GetMeetingDetailsUseCase.Callback meetingDetailsCallback = new GetMeetingDetailsUseCase.Callback() {
        @Override
        public void onMeetingDataLoaded(Meeting meeting) {
            MeetingDetailsPresenter.this.showMeetingDetailsInView(meeting);
            MeetingDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingDetailsPresenter.this.hideViewLoading();
            MeetingDetailsPresenter.this.showErrorMessage(errorBundle);
            MeetingDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MeetingDetailsPresenter(GetMeetingDetailsUseCase getMeetingDetailsUseCase,
                                   MeetingModelDataMapper meetingModelDataMapper) {
        this.getMeetingDetailsUseCase = getMeetingDetailsUseCase;
        this.meetingModelDataMapper = meetingModelDataMapper;
    }

    public void setView(@NonNull CitaDetailView view) {
        this.viewDetailsView = view;
    }

    public void initialize(int meetingId){
        this.meetingId = meetingId;
        this.loadMeetingDetails();
    }

    @Override
    public void resume() {
        this.getMeetingDetails();
    }

    @Override
    public void pause() {
    }

    private void loadMeetingDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMeetingDetails();
    }

    public void editMeeting(int meetingId){
        this.viewDetailsView.editMeeting(meetingId);
    }

    public void deleteMeeting(int meetingId){
        this.viewDetailsView.deleteMeeting(meetingId);
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

    private void showMeetingDetailsInView(Meeting meeting) {
        final MeetingModel meetingModel = this.meetingModelDataMapper.transform(meeting);
        this.viewDetailsView.renderMeeting(meetingModel);
    }

    private void getMeetingDetails() {
        this.getMeetingDetailsUseCase.execute(this.meetingId, this.meetingDetailsCallback);
    }
}
