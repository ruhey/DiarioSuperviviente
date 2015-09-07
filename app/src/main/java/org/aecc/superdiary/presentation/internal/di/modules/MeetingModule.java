package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.SaveMeetingUseCase;
import org.aecc.superdiary.domain.interactor.meeting.SaveMeetingUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.MeetingDetailsNoEditPresenter;
import org.aecc.superdiary.presentation.presenter.MeetingDetailsPresenter;
import org.aecc.superdiary.presentation.presenter.MeetingListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MeetingModule {

    @Provides
    @PerActivity
    GetMeetingListUseCase provideGetMeetingListUseCase(GetMeetingListUseCaseImpl getMeetingListUseCase) {
        return getMeetingListUseCase;
    }

    @Provides
    @PerActivity
    GetMeetingDetailsUseCase provideGetMeetingDetailsUseCase(GetMeetingDetailsUseCaseImpl getMeetingDetailsUseCase) {
        return getMeetingDetailsUseCase;
    }

    @Provides
    @PerActivity
    SaveMeetingUseCase provideSaveMeetingUseCase(SaveMeetingUseCaseImpl saveMeetingUseCase){
        return saveMeetingUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideMeetingListPresenter(MeetingListPresenter meetingListPresenter) {
        return meetingListPresenter;
    }

    @Provides
    @PerActivity
    Presenter provideMeetingDetailsNoEditPresenter(MeetingDetailsNoEditPresenter meetingDetailsNoEditPresenter){
        return meetingDetailsNoEditPresenter;
    }

    @Provides
    @PerActivity
    Presenter provideMeetingDetailsPresenter(MeetingDetailsPresenter meetingDetailsPresenter){
        return meetingDetailsPresenter;
    }

}

