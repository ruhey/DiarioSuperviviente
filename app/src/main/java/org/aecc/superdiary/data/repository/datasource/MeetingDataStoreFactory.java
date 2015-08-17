package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.MeetingCache;
import org.aecc.superdiary.data.database.MeetingDatabaseAPI;
import org.aecc.superdiary.data.database.MeetingDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.MeetingEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MeetingDataStoreFactory {

    private final Context context;
    private final MeetingCache meetingCache;
    private final MeetingEntityDataMapper meetingEntityDataMapper;

    @Inject
    public MeetingDataStoreFactory(Context context, MeetingCache meetingCache, MeetingEntityDataMapper meetingEntityDataMapper) {
        this.context = context;
        this.meetingCache = meetingCache;
        this.meetingEntityDataMapper = meetingEntityDataMapper;
    }

    public MeetingDataStore create(int meetingId) {
        MeetingDataStore meetingDataStore;

        if (!this.meetingCache.isExpired() && this.meetingCache.isCached(meetingId)) {
            //TODO: vamos a suar Cache?
            //meetingDataStore = new DiskMeetingDataStore(this.meetingCache);
            meetingDataStore = createDatabaseDataStore();
        } else {
            meetingDataStore = createDatabaseDataStore();
        }

        return meetingDataStore;
    }

    public MeetingDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        MeetingDatabaseAPI dataBaseAPI = new MeetingDatabaseAPIImpl(this.context);
        return new DatabaseMeetingDataStore(this.meetingCache, dataBaseAPI, meetingEntityDataMapper);
    }
}
