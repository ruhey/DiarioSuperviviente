package org.aecc.superdiary.presentation.internal.di.modules;

import android.content.Context;

import org.aecc.superdiary.data.cache.ContactCache;
import org.aecc.superdiary.data.cache.ContactCacheImpl;
import org.aecc.superdiary.data.cache.ExamCache;
import org.aecc.superdiary.data.cache.ExamCacheImpl;
import org.aecc.superdiary.data.cache.MedicineCache;
import org.aecc.superdiary.data.cache.MedicineCacheImpl;
import org.aecc.superdiary.data.cache.MeetingCache;
import org.aecc.superdiary.data.cache.MeetingCacheImpl;
import org.aecc.superdiary.data.cache.RoutineCache;
import org.aecc.superdiary.data.cache.RoutineCacheImpl;
import org.aecc.superdiary.data.cache.SymptomCache;
import org.aecc.superdiary.data.cache.SymptomCacheImpl;
import org.aecc.superdiary.data.executor.JobExecutor;
import org.aecc.superdiary.data.repository.ContactDataRepository;
import org.aecc.superdiary.data.repository.ExamDataRepository;
import org.aecc.superdiary.data.repository.MedicineDataRepository;
import org.aecc.superdiary.data.repository.MeetingDataRepository;
import org.aecc.superdiary.data.repository.RoutineDataRepository;
import org.aecc.superdiary.data.repository.SymptomDataRepository;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;
import org.aecc.superdiary.domain.repository.ExamRepository;
import org.aecc.superdiary.domain.repository.MedicineRepository;
import org.aecc.superdiary.domain.repository.MeetingRepository;
import org.aecc.superdiary.domain.repository.RoutineRepository;
import org.aecc.superdiary.domain.repository.SymptomRepository;
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

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ContactCache provideContactCache(ContactCacheImpl contactCache) {
        return contactCache;
    }

    @Provides
    @Singleton
    ContactRepository provideContactRepository(ContactDataRepository contactDataRepository) {
        return contactDataRepository;
    }

    @Provides
    @Singleton
    MeetingCache provideMeetingCache(MeetingCacheImpl meetingCache) {
        return meetingCache;
    }

    @Provides
    @Singleton
    MeetingRepository provideMeetingRepository(MeetingDataRepository meetingDataRepository) {
        return meetingDataRepository;
    }

    @Provides
    @Singleton
    MedicineCache provideMedicineCache(MedicineCacheImpl medicineCache) {
        return medicineCache;
    }

    @Provides
    @Singleton
    MedicineRepository provideMedicineRepository(MedicineDataRepository medicineDataRepository) {
        return medicineDataRepository;
    }
    @Provides
    @Singleton
    RoutineCache provideRoutineCache(RoutineCacheImpl routineCache) {
        return routineCache;
    }

    @Provides
    @Singleton
    RoutineRepository provideRoutineRepository(RoutineDataRepository routineDataRepository) {
        return routineDataRepository;
    }
    @Provides
    @Singleton
    ExamCache provideExamCache(ExamCacheImpl examCache) {
        return examCache;
    }

    @Provides
    @Singleton
    ExamRepository provideExamRepository(ExamDataRepository examDataRepository) {
        return examDataRepository;
    }
    @Provides
    @Singleton
    SymptomCache provideSymptomCache(SymptomCacheImpl symptomCache) {
        return symptomCache;
    }

    @Provides
    @Singleton
    SymptomRepository provideSymptomRepository(SymptomDataRepository symptomDataRepository) {
        return symptomDataRepository;
    }
}
