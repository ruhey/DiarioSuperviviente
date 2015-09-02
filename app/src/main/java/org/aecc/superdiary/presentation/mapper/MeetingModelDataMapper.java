package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.MeetingModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class MeetingModelDataMapper {

    @Inject
    public MeetingModelDataMapper() {
    }

    public MeetingModel transform(Meeting meeting) {
        if (meeting == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        MeetingModel meetingModel = new MeetingModel(meeting.getMeetingId());
        meetingModel.setName(meeting.getName());
        meetingModel.setPlace(meeting.getPlace());
        meetingModel.setQuestions(meeting.getQuestions());
        meetingModel.setDateMeeting(meeting.getDateMeeting());
        meetingModel.setHourMeeting(meeting.getHourMeeting());
        meetingModel.setDateAlarm(meeting.getDateAlarm());
        meetingModel.setHourAlarm(meeting.getHourAlarm());
        meetingModel.setDuration(meeting.getDuration());
        meetingModel.setImage(meeting.getImage());

        return meetingModel;
    }

    public Collection<MeetingModel> transform(Collection<Meeting> meetingsCollection) {
        Collection<MeetingModel> meetingModelsCollection;

        if (meetingsCollection != null && !meetingsCollection.isEmpty()) {
            meetingModelsCollection = new ArrayList<MeetingModel>();
            for (Meeting meeting : meetingsCollection) {
                meetingModelsCollection.add(transform(meeting));
            }
        } else {
            meetingModelsCollection = Collections.emptyList();
        }

        return meetingModelsCollection;
    }
}
