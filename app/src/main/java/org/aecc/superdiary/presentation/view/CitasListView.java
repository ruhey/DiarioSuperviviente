package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.MeetingModel;

import java.util.Collection;

public interface CitasListView extends LoadDataView {

    void renderMeetingList(Collection<MeetingModel> meetingModelCollection);

    void viewMeeting(MeetingModel meetingModel);
}
