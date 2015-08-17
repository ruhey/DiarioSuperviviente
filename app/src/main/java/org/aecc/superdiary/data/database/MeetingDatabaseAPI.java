package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.MeetingEntity;

import java.util.Collection;

public interface MeetingDatabaseAPI {

    void getMeetingEntityList(final MeetingListCallback meetingListCallback);

    void getMeetingEntityById(final int meetingId, final MeetingDetailsCallback meetingDetailsCallback);

    void createMeetingEntity(final MeetingEntity meeting, final MeetingCreationCallback meetingCreationCallback);

    void saveMeetingEntity(final MeetingEntity meeting, final MeetingSaveCallback meetingSaveCallback);

    void deleteMeetingEntity(final int meetingId, final MeetingDeletionCallback meetingDeletionCallback);

    interface MeetingListCallback {
        void onMeetingListLoaded(Collection<MeetingEntity> meetingsCollection);

        void onError(Exception exception);
    }

    interface MeetingDetailsCallback {
        void onMeetingEntityLoaded(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingCreationCallback {
        void onMeetingEntityCreated(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingSaveCallback {
        void onMeetingEntitySaved(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }

    interface MeetingDeletionCallback {
        void onMeetingEntityDeleted(Collection<MeetingEntity> meetingsCollection);

        void onError(Exception exception);
    }
}
