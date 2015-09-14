package org.aecc.superdiary.presentation.view.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Meeting;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMeetingComponent;
import org.aecc.superdiary.presentation.internal.di.components.MeetingComponent;
import org.aecc.superdiary.presentation.presenter.MeetingAddPresenter;
import org.aecc.superdiary.presentation.view.CitaAddView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CitaAddActivity extends BaseActivity implements View.OnClickListener, HasComponent<MeetingComponent>, CitaAddView {

    private static final String INTENT_EXTRA_PARAM_MEETING_ID = "org.aecc.INTENT_PARAM_MEETING_ID";
    private static final String INSTANCE_STATE_PARAM_MEETING_ID = "org.aecc.STATE_PARAM_MEETING_ID";


    private MeetingComponent meetingComponent;

    @InjectView(R.id.nombreCita)
    public EditText nombreCita;
    @InjectView(R.id.lugarCita)
    public EditText lugarCita;
    @InjectView(R.id.descripcionCita)
    public EditText descripcionCita;
    @InjectView(R.id.fechaCita)
    public EditText fechaIniCita;
    @InjectView(R.id.fechaAvisoCita)
    public EditText fechaAviso;
    @InjectView(R.id.horaCita)
    public EditText horaIniCita;
    @InjectView(R.id.horaAvisoCita)
    public EditText horaAviso;
    @InjectView(R.id.guardarcita)
    public Button botonGuardarCita;
    @InjectView(R.id.seekBarDuracCitas)
    public SeekBar duration;

    private DatePickerDialog fIniDatePickerDialog;
    private DatePickerDialog fFinDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;
    private TimePickerDialog hFinTimePickerDialog;

    private ScheduleClient scheduleClient;
    private final String TIPO_NOTIFICACION = "C";

    private SimpleDateFormat dateFormatter;

    @Inject
    public MeetingAddPresenter meetingAddPresenter;


    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, CitaAddActivity.class);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_cita);
        ButterKnife.inject(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        scheduleClient = new ScheduleClient(this,TIPO_NOTIFICACION);
        scheduleClient.doBindService();
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }


    @Override
    public void add(String message) {
        this.showToastMessage(message);
        onBackPressed();
    }

    @Override
    public void goToList() {
        this.onBackPressed();
    }

    private void initializeInjector() {
        this.meetingComponent = DaggerMeetingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void initializeActivity(Bundle savedInstanceState) {
        //empty?
    }

    private void setDateTimeField() {
        fechaIniCita.setOnClickListener(this);
        horaIniCita.setOnClickListener(this);
        fechaAviso.setOnClickListener(this);
        horaAviso.setOnClickListener(this);
        botonGuardarCita.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaIniCita.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fFinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaAviso.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaIniCita.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);

        hFinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaAviso.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour, minute, true);
    }

    @Override
    public void onClick(View view) {
        if (view == fechaIniCita) {
            fIniDatePickerDialog.show();
        } else if (view == fechaAviso) {
            fFinDatePickerDialog.show();
        } else if (view == horaIniCita) {
            hIniTimePickerDialog.show();
        } else if (view == horaAviso) {
            hFinTimePickerDialog.show();
        } else if (view == botonGuardarCita) {
            anadirNotificacion();
            addMeeting();
        }
    }

    private void anadirNotificacion() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar newCal = Calendar.getInstance();

        if(TextUtils.isEmpty(fechaAviso.getText()) || TextUtils.isEmpty(horaAviso.getText())){
            Toast.makeText(this, "No se le notificará la hora de su cita, debe completar la fecha y la hora del aviso", Toast.LENGTH_LONG).show();

        }else{
            try {
                newCal.setTime(dateFormat.parse(String.valueOf(fechaAviso.getText()) + " " + String.valueOf(horaAviso.getText())));
                newCal.set(Calendar.SECOND,0);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
            scheduleClient.setAlarmForNotification(newCal);
            // Notify the user what they just did
            Toast.makeText(this, "Notificacion guardada para el "+ fechaAviso.getText() + " a las "+ horaAviso.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

    @Override public void onResume() {
        super.onResume();
        this.meetingAddPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.meetingAddPresenter.pause();
    }

    private void addMeeting() {
        Random rand = new Random();
        int randomNum = rand.nextInt(100);
        Meeting meeting = new Meeting(randomNum);
        meeting.setName(this.nombreCita.getText().toString());
        meeting.setPlace(this.lugarCita.getText().toString());
        meeting.setQuestions(this.descripcionCita.getText().toString());
        meeting.setDateAlarm(this.fechaAviso.getText().toString());
        meeting.setHourAlarm(this.horaAviso.getText().toString());
        meeting.setDateMeeting(this.fechaIniCita.getText().toString());
        meeting.setHourMeeting(this.horaIniCita.getText().toString());
        //meeting.setDuration(this.duration.toString());
        this.meetingAddPresenter.add(meeting);;
    }

    @Override
    public MeetingComponent getComponent() {
        return meetingComponent;
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

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.meetingAddPresenter.setView(this);
        this.meetingAddPresenter.initialize();
    }
}
