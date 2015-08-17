package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;


public interface CreateMeetingUseCase extends Interactor {
    void execute(Meeting meeting, Callback callback);


    interface Callback {
        void onMeetingDataCreated(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }
}