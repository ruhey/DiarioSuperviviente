package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.MeetingEntity;
import org.aecc.superdiary.domain.Meeting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MeetingEntityDataMapper {

    @Inject
    public MeetingEntityDataMapper() {
    }

    public Meeting transform(MeetingEntity meetingEntity) {
        Meeting meeting = null;

        if (meetingEntity != null) {
            meeting = new Meeting(meetingEntity.getMeetingId());
            meeting.setName(meetingEntity.getName());
            meeting.setPlace(meetingEntity.getPlace());
            meeting.setQuestions(meetingEntity.getQuestions());
            meeting.setDateMeeting(meetingEntity.getDateMeeting());
            meeting.setHourMeeting(meetingEntity.getHourMeeting());
            meeting.setDateAlarm(meetingEntity.getDateAlarm());
            meeting.setHourAlarm(meetingEntity.getHourAlarm());
            meeting.setDuration(meetingEntity.getDuration());
            //TODO:ver como hacer lo de la imagen
            meeting.setImage(meetingEntity.getImage());
        }
        return meeting;
    }

    public MeetingEntity untransform(Meeting meeting) {
        MeetingEntity meetingEntity = new MeetingEntity();

        if (meeting != null) {
            meetingEntity.setMeetingId(meeting.getMeetingId());
            meetingEntity.setName(meeting.getName());
            meetingEntity.setPlace(meeting.getPlace());
            meetingEntity.setQuestions(meeting.getQuestions());
            meetingEntity.setDateMeeting(meeting.getDateMeeting());
            meetingEntity.setHourMeeting(meeting.getHourMeeting());
            meetingEntity.setDateAlarm(meeting.getDateAlarm());
            meetingEntity.setHourAlarm(meeting.getHourAlarm());
            meetingEntity.setDuration(meeting.getDuration());
            //TODO:ver como hacer lo de la imagen
            meetingEntity.setImage(meeting.getImage());
        }
        return meetingEntity;
    }

    public Collection<Meeting> transform(Collection<MeetingEntity> meetingEntityCollection) {
        List<Meeting> meetingList = new ArrayList<Meeting>();
        Meeting meeting;
        for (MeetingEntity meetingEntity : meetingEntityCollection) {
            meeting = transform(meetingEntity);
            if (meeting != null) {
                meetingList.add(meeting);
            }
        }
        return meetingList;
    }

    public Collection<MeetingEntity> untransform(Collection<Meeting> meetingCollection) {
        List<MeetingEntity> meetingEntityList = new ArrayList<MeetingEntity>();
        MeetingEntity meetingEntity;
        for (Meeting meeting : meetingCollection) {
            meetingEntity = untransform(meeting);
            if (meetingEntity != null) {
                meetingEntityList.add(meetingEntity);
            }
        }
        return meetingEntityList;
    }
}
