package org.aecc.superdiary.domain.interactor.meeting;


import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

public interface SaveMeetingUseCase extends Interactor {

    void execute(Meeting meeting, Callback callback);


    interface Callback {
        void onMeetingDataSaved(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }

}
