package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.ContactEntity;

public interface ContactCache {

    void get(final int contactId, final ContactCacheCallback callback);

    void put(ContactEntity contactEntity);

    boolean isCached(final int contactId);

    boolean isExpired();

    void evictAll();

    interface ContactCacheCallback {
        void onContactEntityLoaded(ContactEntity contactEntity);

        void onError(Exception exception);
    }
}
