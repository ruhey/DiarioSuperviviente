package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.MeetingRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetMeetingListUseCaseImpl implements GetMeetingListUseCase {

    private final MeetingRepository meetingRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private final MeetingRepository.MeetingListCallback repositoryCallback =
            new MeetingRepository.MeetingListCallback() {
                @Override
                public void onMeetingListLoaded(Collection<Meeting> meetingsCollection) {
                    notifyGetMeetingListSuccessfully(meetingsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetMeetingListUseCaseImpl(MeetingRepository meetingRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.meetingRepository = meetingRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.meetingRepository.getMeetingList(this.repositoryCallback);
    }

    private void notifyGetMeetingListSuccessfully(final Collection<Meeting> meetingsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onMeetingListLoaded(meetingsCollection);
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
