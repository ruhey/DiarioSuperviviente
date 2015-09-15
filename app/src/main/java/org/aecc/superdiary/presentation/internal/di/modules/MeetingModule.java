package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.CreateMeetingUseCase;
import org.aecc.superdiary.domain.interactor.meeting.CreateMeetingUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.DeleteMeetingUseCase;
import org.aecc.superdiary.domain.interactor.meeting.DeleteMeetingUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCase;
import org.aecc.superdiary.domain.interactor.meeting.GetMeetingListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.meeting.SaveMeetingUseCase;
import org.aecc.superdiary.domain.interactor.meeting.SaveMeetingUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.MeetingAddPresenter;
import org.aecc.superdiary.presentation.presenter.MeetingDeletePresenter;
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
    CreateMeetingUseCase provideCreateMeetingUseCase(CreateMeetingUseCaseImpl createMeetingUseCase){
        return createMeetingUseCase;
    }

    @Provides
    @PerActivity
    DeleteMeetingUseCase provideDeleteMeetingUseCase(DeleteMeetingUseCaseImpl deleteMeetingUseCase){
        return deleteMeetingUseCase;
    }

    @Provides
    @PerActivity
    GetContactDetailsUseCase provideGetContactDetailsUseCase(GetContactDetailsUseCaseImpl getContactDetailsUseCase) {
        return getContactDetailsUseCase;
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

    @Provides
    @PerActivity
    Presenter provideMeetingAddPresenter(MeetingAddPresenter meetingAddPresenter){
        return meetingAddPresenter;
    }

    @Provides
    @PerActivity
    Presenter provideMeetingDeletePresenter(MeetingDeletePresenter meetingDeletePresenter){
        return meetingDeletePresenter;
    }
}

