package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MeetingRepository;

import javax.inject.Inject;

public class GetMeetingDetailsUseCaseImpl implements GetMeetingDetailsUseCase {

    private final MeetingRepository meetingRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int meetingId = -1;
    private Callback callback;
    private final MeetingRepository.MeetingDetailsCallback repositoryCallback =
            new MeetingRepository.MeetingDetailsCallback() {
                @Override
                public void onMeetingLoaded(Meeting meeting) {
                    notifyGetMeetingDetailsSuccessfully(meeting);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetMeetingDetailsUseCaseImpl(MeetingRepository meetingRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.meetingRepository = meetingRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int meetingId, Callback callback) {
        if (meetingId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.meetingId = meetingId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.meetingRepository.getMeetingById(this.meetingId, this.repositoryCallback);
    }

    private void notifyGetMeetingDetailsSuccessfully(final Meeting meeting) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMeetingDataLoaded(meeting);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
