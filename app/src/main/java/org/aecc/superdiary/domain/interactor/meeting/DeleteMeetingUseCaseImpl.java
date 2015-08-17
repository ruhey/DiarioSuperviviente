package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MeetingRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteMeetingUseCaseImpl implements DeleteMeetingUseCase {

    private final MeetingRepository meetingRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int meetingId = -1;
    private Callback callback;
    private final MeetingRepository.MeetingDetionCallback repositoryCallback =
            new MeetingRepository.MeetingDetionCallback() {
                @Override
                public void onMeetingDeleted(Collection<Meeting> meetingsCollection) {
                    notifyDeleteMeetingSuccessfully(meetingsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public DeleteMeetingUseCaseImpl(MeetingRepository meetingRepository, ThreadExecutor threadExecutor,
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
        this.meetingRepository.deleteMeeting(this.meetingId, this.repositoryCallback);
    }

    private void notifyDeleteMeetingSuccessfully(final Collection<Meeting> meetingsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMeetingDataDeleted(meetingsCollection);
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
