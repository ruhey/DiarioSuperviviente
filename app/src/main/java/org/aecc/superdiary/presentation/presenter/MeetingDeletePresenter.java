package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.medicine.DeleteMedicineUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.meeting.DeleteMeetingUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.MedicineModelDataMapper;
import org.aecc.superdiary.presentation.mapper.MeetingModelDataMapper;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.view.CitaDeleteView;
import org.aecc.superdiary.presentation.view.MedicamentoDetailDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class MeetingDeletePresenter implements Presenter {

    private final GetMeetingDetailsUseCase getMeetingDetailsUseCase;
    private final DeleteMeetingUseCase deleteMeetingUseCase;
    private final MeetingModelDataMapper meetingModelDataMapper;
    private int meetingId;
    private CitaDeleteView viewDetailsView;

    private final GetMeetingDetailsUseCase.Callback getMeetingDetailsUseCaseCallback = new GetMeetingDetailsUseCase.Callback(){

        @Override
        public void onMeetingDataLoaded(Meeting meeting) {
            MeetingDeletePresenter.this.showMeetingDetailsInView(meeting);
            MeetingDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingDeletePresenter.this.hideViewLoading();
            MeetingDeletePresenter.this.showErrorMessage(errorBundle);
            MeetingDeletePresenter.this.showViewRetry();
        }
    };

    private final DeleteMeetingUseCase.Callback deleteMeetingUseCaseCallback = new DeleteMeetingUseCase.Callback(){

        @Override
        public void onMeetingDataDeleted(Collection<Meeting> meetingsCollection) {
            MeetingDeletePresenter.this.hideViewLoading();
            MeetingDeletePresenter.this.showOKMessage();
            MeetingDeletePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            MeetingDeletePresenter.this.hideViewLoading();
            MeetingDeletePresenter.this.showErrorMessage(errorBundle);
            MeetingDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public MeetingDeletePresenter(GetMeetingDetailsUseCase getMeetingDetailsUseCase,
                                  DeleteMeetingUseCase deleteMeetingUseCase,
                                  MeetingModelDataMapper meetingModelDataMapper){
        this.getMeetingDetailsUseCase = getMeetingDetailsUseCase;
        this.deleteMeetingUseCase = deleteMeetingUseCase;
        this.meetingModelDataMapper = meetingModelDataMapper;
    }

    public void setView(@NonNull CitaDeleteView view) {
        this.viewDetailsView = view;
    }

    public void initialize(int meetingId){
        this.meetingId = meetingId;
        this.loadMeetingDetails();
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

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    private void showOKMessage() {
        this.viewDetailsView.showOkMessage();
    }

    public void deleteMeeting(int meetingId){
        this.persistDeletion(meetingId);
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
        this.getMeetingDetailsUseCase.execute(this.meetingId, this.getMeetingDetailsUseCaseCallback);
    }

    private void persistDeletion(int meetingId) {
        this.deleteMeetingUseCase.execute(this.meetingId, this.deleteMeetingUseCaseCallback);
    }

    private void goBack() {
        this.viewDetailsView.goToList();
    }
}
