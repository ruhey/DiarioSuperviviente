package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface MeetingRepository {

    void getMeetingList(MeetingListCallback MeetingListCallback);

    void getMeetingById(final int meetingId, MeetingDetailsCallback meetingCallback);

    void createMeeting(final Meeting meeting, MeetingCreationCallback meetingCallback);

    void saveMeeting(final Meeting meeting, MeetingSaveCallback meetingCallback);

    void deleteMeeting(final int meetingId, MeetingDetionCallback meetingDeletionCallback);

    interface MeetingListCallback {
        void onMeetingListLoaded(Collection<Meeting> meetingsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface MeetingDetailsCallback {
        void onMeetingLoaded(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }

    interface MeetingCreationCallback {
        void onMeetingCreated(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }

    interface MeetingSaveCallback {
        void onMeetingSaved(Meeting meeting);

        void onError(ErrorBundle errorBundle);
    }

    interface MeetingDetionCallback {
        void onMeetingDeleted(Collection<Meeting> meetingsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
