package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
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
    Presenter provideMeetingListPresenter(MeetingListPresenter meetingListPresenter) {
        return meetingListPresenter;
    }

}

