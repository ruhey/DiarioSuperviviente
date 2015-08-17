package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.MeetingEntity;

public interface MeetingCache {

    void get(final int meetingId, final MeetingCacheCallback callback);

    void put(MeetingEntity meetingEntity);

    boolean isCached(final int meetingId);

    boolean isExpired();

    void evictAll();

    interface MeetingCacheCallback {
        void onMeetingEntityLoaded(MeetingEntity meetingEntity);

        void onError(Exception exception);
    }
}
