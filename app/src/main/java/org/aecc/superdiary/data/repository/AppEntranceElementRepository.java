package org.aecc.superdiary.data.repository;


import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.AppEntranceElementDataStore;
import org.aecc.superdiary.data.repository.datasource.AppEntranceElementDataStoreFactory;
import org.aecc.superdiary.domain.AppEntranceElement;
import org.aecc.superdiary.domain.repository.AppEntranceElementDomainRepository;

import java.util.Collection;

import javax.inject.Inject;

public class AppEntranceElementRepository implements AppEntranceElementDomainRepository {

    private final AppEntranceElementDataStoreFactory appEntranceElementDataStoreFactory;

    @Inject
    public AppEntranceElementRepository(AppEntranceElementDataStoreFactory appEntranceElementDataStoreFactory){
        this.appEntranceElementDataStoreFactory = appEntranceElementDataStoreFactory;
    }

    @Override
    public void getAppEntranceElementsList(final AppEntranceElementCallback appEntranceElementCallback) {
        final AppEntranceElementDataStore appEntranceElementDataStore = this.appEntranceElementDataStoreFactory.createDatabaseDataStore();
        appEntranceElementDataStore.getappEntranceElementList(new AppEntranceElementDataStore.AppEntranceElementListCallback(){

            @Override
            public void appEntranceElementListLoaded(Collection<AppEntranceElement> appEntranceElementCollection){
                appEntranceElementCallback.onAppElementsEntranceItemsLoaded(appEntranceElementCollection);           }

            @Override
            public void onError(Exception exception){new RepositoryErrorBundle(exception);}
        });
    }
}
