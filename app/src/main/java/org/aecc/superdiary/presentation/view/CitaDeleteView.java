package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MeetingModel;

public interface CitaDeleteView extends LoadDataView {

    void renderMeeting(MeetingModel meeting);

    void deleteMeeting(int meetingId);

    void goToList();
}
