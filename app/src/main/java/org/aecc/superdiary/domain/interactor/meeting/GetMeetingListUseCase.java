package org.aecc.superdiary.domain.interactor.meeting;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.Interactor;

import java.util.Collection;


public interface GetMeetingListUseCase extends Interactor {

    void execute(Callback callback);

    interface Callback {
        void onMeetingListLoaded(Collection<Meeting> meetingsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
