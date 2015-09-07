package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;
import android.content.Intent;

import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.view.CitaDeleteView;
import org.aecc.superdiary.presentation.view.CitaDetailView;

public class CitaDeleteActivity extends DiaryBaseActivity implements CitaDeleteView{

    private static final String INTENT_EXTRA_PARAM_MEETING_ID = "org.aecc.INTENT_PARAM_MEETING_ID";
    private static final String INSTANCE_STATE_PARAM_MEETING_ID = "org.aecc.STATE_PARAM_MEETING_ID";

    public static Intent getCallingIntent(Context context, int meetingId) {
        Intent callingIntent = new Intent(context, CitaDeleteActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MEETING_ID, meetingId);
        return callingIntent;
    }

    @Override
    public void renderMeeting(MeetingModel meeting) {

    }

    @Override
    public void deleteMeeting(int meetingId) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
