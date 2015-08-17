package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface GetMeetingDetailsUseCase extends Interactor {
    void execute(int meetingId, Callback callback);


    interface Callback {
        void onMeetingDataLoaded(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }
}
