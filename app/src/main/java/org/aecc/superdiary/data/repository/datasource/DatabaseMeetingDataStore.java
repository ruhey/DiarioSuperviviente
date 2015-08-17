package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.MeetingCache;
import org.aecc.superdiary.data.database.MeetingDatabaseAPI;
import org.aecc.superdiary.data.entity.MeetingEntity;
import org.aecc.superdiary.data.entity.mapper.MeetingEntityDataMapper;
import org.aecc.superdiary.domain.Meeting;

import java.util.Collection;

public class DatabaseMeetingDataStore implements MeetingDataStore {
    private final MeetingCache meetingCache;
    private final MeetingDatabaseAPI databaseAPI;
    private final MeetingEntityDataMapper meetingEntityDataMapper;

    public DatabaseMeetingDataStore(MeetingCache meetingCache, MeetingDatabaseAPI databaseAPI, MeetingEntityDataMapper meetingEntityDataMapper) {
        this.meetingCache = meetingCache;
        this.databaseAPI = databaseAPI;
        this.meetingEntityDataMapper = meetingEntityDataMapper;

    }

    @Override
    public void getMeetingsEntityList(final MeetingListCallback meetingListCallback) {
        this.databaseAPI.getMeetingEntityList(new MeetingDatabaseAPI.MeetingListCallback() {

            @Override
            public void onMeetingListLoaded(Collection<MeetingEntity> meetingsCollection) {
                meetingListCallback.onMeetingListLoaded(meetingsCollection);
            }

            @Override
            public void onError(Exception exception) {
                meetingListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getMeetingEntityDetails(int id, final MeetingDetailsCallback meetingDetailsCallback) {
        this.databaseAPI.getMeetingEntityById(id, new MeetingDatabaseAPI.MeetingDetailsCallback() {

            @Override
            public void onMeetingEntityLoaded(MeetingEntity meetingEntity) {
                meetingDetailsCallback.onMeetingEntityLoaded(meetingEntity);
                //CloudUserDataStore.this.putMeetingEntityInCache(meetingEntity);
            }

            @Override
            public void onError(Exception exception) {
                meetingDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createMeetingEntity(final Meeting meeting, final MeetingCreationCallback meetingCreationCallback) {
        this.databaseAPI.createMeetingEntity(this.meetingEntityDataMapper.untransform(meeting), new MeetingDatabaseAPI.MeetingCreationCallback() {

            @Override
            public void onMeetingEntityCreated(MeetingEntity meetingEntity) {
                meetingCreationCallback.onMeetingCreated(meetingEntity);
            }

            @Override
            public void onError(Exception exception) {
                meetingCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveMeetingEntity(final Meeting meeting, final MeetingSaveCallback meetingSaveCallback) {
        this.databaseAPI.saveMeetingEntity(this.meetingEntityDataMapper.untransform(meeting), new MeetingDatabaseAPI.MeetingSaveCallback() {
            @Override
            public void onMeetingEntitySaved(MeetingEntity meetingEntity) {
                meetingSaveCallback.onMeetingSaved(meetingEntity);
            }

            @Override
            public void onError(Exception exception) {
                meetingSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteMeetingEntity(final int id, final MeetingDeletionCallback meetingDeletionCallback) {
        this.databaseAPI.deleteMeetingEntity(id, new MeetingDatabaseAPI.MeetingDeletionCallback() {
            @Override
            public void onMeetingEntityDeleted(Collection<MeetingEntity> meetingsCollection) {
                meetingDeletionCallback.onMeetingDeleted(meetingsCollection);
            }

            @Override
            public void onError(Exception exception) {
                meetingDeletionCallback.onError(exception);
            }
        });
    }


}
