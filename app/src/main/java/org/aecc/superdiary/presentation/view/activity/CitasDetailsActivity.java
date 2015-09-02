package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MeetingComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMeetingComponent;

public class CitasDetailsActivity extends BaseActivity implements HasComponent<MeetingComponent> {

    private static final String INTENT_EXTRA_PARAM_MEETING_ID = "org.aecc.INTENT_PARAM_MEETING_ID";
    private static final String INSTANCE_STATE_PARAM_MEETING_ID = "org.aecc.STATE_PARAM_MEETING_ID";

    private int meetingId;
    private MeetingComponent meetingComponent;

    public static Intent getCallingIntent(Context context, int meetingId) {
        Intent callingIntent = new Intent(context, CitasDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MEETING_ID, meetingId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_MEETING_ID, this.meetingId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.meetingId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_MEETING_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.meetingId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_MEETING_ID);
        }
    }

    private void initializeInjector() {
        this.meetingComponent = DaggerMeetingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MeetingComponent getComponent() {
        return meetingComponent;
    }

}
