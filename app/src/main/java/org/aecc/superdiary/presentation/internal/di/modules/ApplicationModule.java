package org.aecc.superdiary.presentation.internal.di.modules;

import android.content.Context;

import org.aecc.superdiary.data.cache.ContactCache;
import  org.aecc.superdiary.data.cache.ContactCacheImpl;
import org.aecc.superdiary.data.executor.JobExecutor;
import org.aecc.superdiary.data.repository.ContactDataRepository;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;
import org.aecc.superdiary.presentation.AndroidApplication;
import org.aecc.superdiary.presentation.UIThread;
import org.aecc.superdiary.presentation.navigator.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    ContactCache provideContactCache(ContactCacheImpl contactCache) {
        return contactCache;
    }

    @Provides @Singleton
    ContactRepository provideContactRepository(ContactDataRepository contactDataRepository) {
        return contactDataRepository;
    }
}
