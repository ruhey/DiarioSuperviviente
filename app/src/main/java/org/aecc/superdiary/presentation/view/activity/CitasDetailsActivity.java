package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MeetingComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMeetingComponent;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.presenter.MeetingDetailsNoEditPresenter;
import org.aecc.superdiary.presentation.presenter.MeetingDetailsPresenter;
import org.aecc.superdiary.presentation.view.CitaDetailView;
import org.aecc.superdiary.presentation.view.CitaNoEditView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CitasDetailsActivity extends BaseActivity implements HasComponent<MeetingComponent>, CitaDetailView {

    private static final String INTENT_EXTRA_PARAM_MEETING_ID = "org.aecc.INTENT_PARAM_MEETING_ID";
    private static final String INSTANCE_STATE_PARAM_MEETING_ID = "org.aecc.STATE_PARAM_MEETING_ID";

    private int meetingId;
    private MeetingComponent meetingComponent;

    @InjectView(R.id.nombreCita)
    TextView nombreCita;
    @InjectView(R.id.lugarCita)
    TextView lugarCita;
    @InjectView(R.id.descripcionCita)
    TextView descripcionCita;
    @InjectView(R.id.fechaCita)
    TextView fechaCita;
    @InjectView(R.id.horaCita)
    TextView horaCita;
    @InjectView(R.id.fechaAvisoCita)
    TextView fechaAvisoCita;
    @InjectView(R.id.horaAvisoCita)
    TextView horaAvisoCita;
    @InjectView(R.id.editarCita)
    Button editarCita;
    @InjectView(R.id.borrarCita)
    Button borrarCita;

    @Inject
    public MeetingDetailsPresenter meetingDetailsPresenter;

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
        setContentView(R.layout.activity_cita_noedit);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
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


    @Override
    public void renderMeeting(MeetingModel meeting) {

        this.nombreCita.setText(meeting.getName());
        this.lugarCita.setText(meeting.getPlace());
        this.descripcionCita.setText(meeting.getQuestions());
        this.fechaCita.setText(meeting.getDateMeeting());
        this.horaCita.setText(meeting.getHourMeeting());
        this.fechaAvisoCita.setText(meeting.getDateAlarm());
        this.horaAvisoCita.setText(meeting.getHourAlarm());
    }

    @OnClick(R.id.editarCita)
    public void editMeeting(){
        this.meetingDetailsPresenter.editMeeting(this.meetingId);
    }

    public void editMeeting(int meetingId){
        this.navigator.navigateToMeetingEdit(getApplicationContext(), this.meetingId);
    }

    @OnClick(R.id.borrarCita)
    public void deleteMeeting(){
        this.meetingDetailsPresenter.deleteMeeting(this.meetingId);
    }

    public void deleteMeeting(int meetingId){
        this.navigator.navigateToMeetingDelete(getApplicationContext(), this.meetingId);
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
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override public void onResume() {
        super.onResume();
        this.meetingDetailsPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.meetingDetailsPresenter.pause();
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.meetingDetailsPresenter.setView(this);
        this.meetingDetailsPresenter.initialize(this.meetingId);
    }
}
