package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.presentation.model.MeetingModel;

public interface CitaDetailView extends LoadDataView {

    void renderMeeting(MeetingModel meeting);

    void editMeeting(int meetingId);

    void deleteMeeting(int meetingId);

    void reloadContactDetails(Contact contact);
}
