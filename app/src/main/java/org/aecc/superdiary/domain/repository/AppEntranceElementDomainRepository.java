package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.AppEntranceElement;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface AppEntranceElementDomainRepository {

    void getAppEntranceElementsList(AppEntranceElementCallback appEntranceElementCallback);

    interface AppEntranceElementCallback {

        void onAppElementsEntranceItemsLoaded(Collection<AppEntranceElement> appEntranceElementsList);

        void onError(ErrorBundle errorBundle);
    }
}
