package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MeetingModel;

public interface CitaNoEditView extends LoadDataView {

    void renderMeeting(MeetingModel meeting);

    void saveMeeting(MeetingModel meeting);

    void goBack();
}
