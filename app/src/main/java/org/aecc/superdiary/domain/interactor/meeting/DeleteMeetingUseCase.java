package org.aecc.superdiary.domain.interactor.meeting;


import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;

public interface DeleteMeetingUseCase extends Interactor {
    void execute(int meetingId, Callback callback);


    interface Callback {
        void onMeetingDataDeleted(Collection<Meeting> meetingsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
