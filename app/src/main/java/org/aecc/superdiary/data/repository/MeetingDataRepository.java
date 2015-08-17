package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.MeetingEntity;
import org.aecc.superdiary.data.entity.mapper.MeetingEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateMeetingException;
import org.aecc.superdiary.data.exception.CantSaveMeetingException;
import org.aecc.superdiary.data.exception.MeetingNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.MeetingDataStore;
import org.aecc.superdiary.data.repository.datasource.MeetingDataStoreFactory;
import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.domain.repository.MeetingRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MeetingDataRepository implements MeetingRepository {

    private final MeetingDataStoreFactory meetingDataStoreFactory;
    private final MeetingEntityDataMapper meetingEntityDataMapper;

    @Inject
    public MeetingDataRepository(MeetingDataStoreFactory meetingDataStoreFactory, MeetingEntityDataMapper meetingEntityDataMapper) {
        this.meetingDataStoreFactory = meetingDataStoreFactory;
        this.meetingEntityDataMapper = meetingEntityDataMapper;
    }

    @Override
    public void getMeetingList(final MeetingListCallback meetingListCallback) {
        final MeetingDataStore meetingDataStore = this.meetingDataStoreFactory.createDatabaseDataStore();
        meetingDataStore.getMeetingsEntityList(new MeetingDataStore.MeetingListCallback() {
            @Override
            public void onMeetingListLoaded(Collection<MeetingEntity> meetingsCollection) {
                Collection<Meeting> meetings =
                        MeetingDataRepository.this.meetingEntityDataMapper.transform(meetingsCollection);
                meetingListCallback.onMeetingListLoaded(meetings);
            }

            @Override
            public void onError(Exception exception) {
                meetingListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getMeetingById(final int meetingId, final MeetingDetailsCallback meetingCallback) {
        MeetingDataStore meetingDataStore = this.meetingDataStoreFactory.create(meetingId);
        meetingDataStore.getMeetingEntityDetails(meetingId, new MeetingDataStore.MeetingDetailsCallback() {
            @Override
            public void onMeetingEntityLoaded(MeetingEntity meetingEntity) {
                Meeting meeting = MeetingDataRepository.this.meetingEntityDataMapper.transform(meetingEntity);
                if (meeting != null) {
                    meetingCallback.onMeetingLoaded(meeting);
                } else {
                    meetingCallback.onError(new RepositoryErrorBundle(new MeetingNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                meetingCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createMeeting(final Meeting meeting, final MeetingCreationCallback meetingCreateCallback) {
        final MeetingDataStore meetingDataStore = this.meetingDataStoreFactory.createDatabaseDataStore();
        meetingDataStore.createMeetingEntity(meeting, new MeetingDataStore.MeetingCreationCallback() {

            @Override
            public void onMeetingCreated(MeetingEntity meetingEntity) {
                Meeting meeting = MeetingDataRepository.this.meetingEntityDataMapper.transform(meetingEntity);
                if (meeting != null) {
                    meetingCreateCallback.onMeetingCreated(meeting);
                } else {
                    meetingCreateCallback.onError(new RepositoryErrorBundle(new CantCreateMeetingException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                meetingCreateCallback.onError(new RepositoryErrorBundle(new CantCreateMeetingException()));
            }
        });
    }

    @Override
    public void saveMeeting(final Meeting meeting, final MeetingSaveCallback meetingSaveCallback) {
        final MeetingDataStore meetingDataStore = this.meetingDataStoreFactory.createDatabaseDataStore();
        meetingDataStore.saveMeetingEntity(meeting, new MeetingDataStore.MeetingSaveCallback() {

            @Override
            public void onMeetingSaved(MeetingEntity meetingEntity) {
                Meeting meeting = MeetingDataRepository.this.meetingEntityDataMapper.transform(meetingEntity);
                if (meeting != null) {
                    meetingSaveCallback.onMeetingSaved(meeting);
                } else {
                    meetingSaveCallback.onError(new RepositoryErrorBundle(new CantSaveMeetingException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                meetingSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteMeeting(final int meetingId, final MeetingDetionCallback meetingDeletionCallback) {
        final MeetingDataStore meetingDataStore = this.meetingDataStoreFactory.createDatabaseDataStore();
        meetingDataStore.deleteMeetingEntity(meetingId, new MeetingDataStore.MeetingDeletionCallback() {
            @Override
            public void onMeetingDeleted(Collection<MeetingEntity> meetingsCollection) {
                Collection<Meeting> meetings =
                        MeetingDataRepository.this.meetingEntityDataMapper.transform(meetingsCollection);
                meetingDeletionCallback.onMeetingDeleted(meetings);
            }

            @Override
            public void onError(Exception exception) {
                meetingDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
