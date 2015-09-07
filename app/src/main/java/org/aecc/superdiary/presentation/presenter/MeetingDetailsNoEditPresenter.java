package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.domain.interactor.meeting.SaveMeetingUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MeetingModelDataMapper;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.view.CitaNoEditView;

import javax.inject.Inject;

public class MeetingDetailsNoEditPresenter implements Presenter {

    private final GetMeetingDetailsUseCase getMeetingDetailsUseCase;
    private final SaveMeetingUseCase saveMeetingUseCase;
    private final MeetingModelDataMapper meetingModelDataMapper;
    private int meetingId;
    private CitaNoEditView viewDetailsView;
    private final GetMeetingDetailsUseCase.Callback meetingDetailsCallback = new GetMeetingDetailsUseCase.Callback() {
        @Override
        public void onMeetingDataLoaded(Meeting meeting) {
            MeetingDetailsNoEditPresenter.this.showMeetingDetailsInView(meeting);
            MeetingDetailsNoEditPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingDetailsNoEditPresenter.this.hideViewLoading();
            MeetingDetailsNoEditPresenter.this.showErrorMessage(errorBundle);
            MeetingDetailsNoEditPresenter.this.showViewRetry();
        }
    };

    private final SaveMeetingUseCase.Callback saveMeetingCallback = new SaveMeetingUseCase.Callback(){

        @Override
        public void onMeetingDataSaved(Meeting meeting){
            MeetingDetailsNoEditPresenter.this.showStoredDataOK();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingDetailsNoEditPresenter.this.hideViewLoading();
            MeetingDetailsNoEditPresenter.this.showErrorMessage(errorBundle);
            MeetingDetailsNoEditPresenter.this.showViewRetry();
        }
    };

    private void showStoredDataOK() {
        this.viewDetailsView.showError("Contacto guardado correctamente");
    }

    @Inject
    public MeetingDetailsNoEditPresenter (GetMeetingDetailsUseCase getMeetingDetailsUseCase,
                                          SaveMeetingUseCase saveMeetingUseCase,
                                          MeetingModelDataMapper meetingModelDataMapper){
        this.getMeetingDetailsUseCase = getMeetingDetailsUseCase;
        this.saveMeetingUseCase = saveMeetingUseCase;
        this.meetingModelDataMapper = meetingModelDataMapper;
    }

    public void setView(@NonNull CitaNoEditView view) {
        this.viewDetailsView = view;
    }

    public void initialize(int meetingId){
        this.meetingId = meetingId;
        this.loadMeetingDetails();
    }

    public void saveMeeting(Meeting meeting){
        this.perssistMeeting(meeting);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void loadMeetingDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMeetingDetails();
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

    private void perssistMeeting(Meeting meeting){
     this.saveMeetingUseCase.execute(meeting, this.saveMeetingCallback);
    }
}
