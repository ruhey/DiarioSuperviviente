package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.MeetingEntity;
import org.aecc.superdiary.domain.Meeting;

import java.util.Collection;

public interface MeetingDataStore {

    void getMeetingsEntityList(MeetingListCallback meetingListCallback);

    void getMeetingEntityDetails(int id, MeetingDetailsCallback meetingDetailsCallback);

    void createMeetingEntity(Meeting meeting, MeetingCreationCallback meetingCallback);

    void saveMeetingEntity(Meeting meeting, MeetingSaveCallback meetingCallback);

    void deleteMeetingEntity(int id, MeetingDeletionCallback meetingDeletionCallback);

    interface MeetingListCallback {
        void onMeetingListLoaded(Collection<MeetingEntity> meetingsCollection);

        void onError(Exception exception);
    }

    interface MeetingDetailsCallback {
        void onMeetingEntityLoaded(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingCreationCallback {
        void onMeetingCreated(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingSaveCallback {
        void onMeetingSaved(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingDeletionCallback {
        void onMeetingDeleted(Collection<MeetingEntity> meetingsCollection);

        void onError(Exception exception);
    }
}
