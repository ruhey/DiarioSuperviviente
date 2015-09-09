package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.meeting.CreateMeetingUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MeetingModelDataMapper;
import org.aecc.superdiary.presentation.view.CitaAddView;

import javax.inject.Inject;

public class MeetingAddPresenter implements Presenter {

    private final CreateMeetingUseCase createMeetingUseCase;
    private final MeetingModelDataMapper meetingModelDataMapper;
    private CitaAddView viewDetailsView;
    private final CreateMeetingUseCase.Callback createMeetingCallback = new CreateMeetingUseCase.Callback() {
        @Override
        public void onMeetingDataCreated(Meeting meeting){
            MeetingAddPresenter.this.showStoredDataOK();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingAddPresenter.this.hideViewLoading();
            MeetingAddPresenter.this.showErrorMessage(errorBundle);
            MeetingAddPresenter.this.showViewRetry();
        }
    };

    @Inject
    public MeetingAddPresenter (CreateMeetingUseCase createMeetingUseCase, MeetingModelDataMapper meetingModelDataMapper){
        this.createMeetingUseCase = createMeetingUseCase;
        this.meetingModelDataMapper = meetingModelDataMapper;
    }

    private void showStoredDataOK() {
        this.viewDetailsView.showError("Cita creada correctamente");
    }

    public void setView(@NonNull CitaAddView view) {
        this.viewDetailsView = view;
    }

    public void initialize(){

    }

    public void add(Meeting meeting){
        if(meeting.getName() == null ||
                meeting.getDateMeeting()== null ||
                meeting.getQuestions() == null ||
                meeting.getPlace() == null ||
                meeting.getDateAlarm() == null ||
                meeting.getHourAlarm() == null ||
                //meeting.getDuration() == null ||
                meeting.getHourMeeting()== null) {
            this.viewDetailsView.showError("Los campos no pueden estar vacios.");
        }else {
            this.perssistMeeting(meeting);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

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

    private void perssistMeeting(Meeting meeting){
        this.createMeetingUseCase.execute(meeting, this.createMeetingCallback);
    }

}
