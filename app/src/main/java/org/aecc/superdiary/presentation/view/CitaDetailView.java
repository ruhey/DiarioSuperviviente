package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MeetingModel;

public interface CitaDetailView extends LoadDataView {

    void renderMeeting(MeetingModel meeting);
}
