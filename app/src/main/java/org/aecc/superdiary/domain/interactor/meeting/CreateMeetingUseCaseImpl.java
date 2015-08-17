package org.aecc.superdiary.domain.interactor.meeting;


import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MeetingRepository;

import javax.inject.Inject;

public class CreateMeetingUseCaseImpl implements CreateMeetingUseCase {

    private final MeetingRepository meetingRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Meeting meeting = null;
    private Callback callback;
    private final MeetingRepository.MeetingCreationCallback repositoryCallback =
            new MeetingRepository.MeetingCreationCallback() {
                @Override
                public void onMeetingCreated(Meeting meeting) {
                    notifyCreateMeetingSuccessfully(meeting);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public CreateMeetingUseCaseImpl(MeetingRepository meetingRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.meetingRepository = meetingRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Meeting meeting, Callback callback) {
        if (meeting == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.meeting = meeting;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.meetingRepository.createMeeting(this.meeting, this.repositoryCallback);
    }

    private void notifyCreateMeetingSuccessfully(final Meeting meeting) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMeetingDataCreated(meeting);
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
