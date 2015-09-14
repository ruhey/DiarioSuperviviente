package org.aecc.superdiary.presentation.view.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerRoutineComponent;
import org.aecc.superdiary.presentation.internal.di.components.RoutineComponent;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.presenter.RoutineDetailEditPresenter;
import org.aecc.superdiary.presentation.view.RutinaDetailEditView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RutinaEditActivity extends BaseActivity implements RutinaDetailEditView,HasComponent<RoutineComponent>{

    private static final String INTENT_EXTRA_PARAM_ROUTINE_ID = "org.aecc.INTENT_PARAM_ROUTINE_ID";
    private static final String INSTANCE_STATE_PARAM_ROUTINE_ID = "org.aecc.STATE_PARAM_ROUTINE_ID";

    private int routineId;
    private RoutineComponent routineComponent;

    @InjectView(R.id.nombreRutina)
    EditText nombreRutina;
    @InjectView(R.id.lugarRutina)
    EditText lugarRutina;
    @InjectView(R.id.descripcionRutina)
    EditText descripcionRutina;
    @InjectView(R.id.fechaRutina)
    EditText fechaRutina;
    @InjectView(R.id.fechaAvisoRutina)
    EditText fechaAvisoRutina;
    @InjectView(R.id.horaRutina)
    EditText horaRutina;
    @InjectView(R.id.horaAvisoRutina)
    EditText horaAvisoRutina;
    @InjectView(R.id.guardarRutina)
    Button guardarRutina;

    private DatePickerDialog fIniDatePickerDialog;
    private DatePickerDialog fFinDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;
    private TimePickerDialog hFinTimePickerDialog;
    
    private ScheduleClient scheduleClient;
    private final String TIPO_NOTIFICACION = "R";

    private SimpleDateFormat dateFormatter;
    
    @Inject
    RoutineDetailEditPresenter routineDetailEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_rutina);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this,TIPO_NOTIFICACION);
        scheduleClient.doBindService();
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    public static Intent getCallingIntent(Context context, int routineId) {
        Intent callingIntent = new Intent(context, RutinaEditActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ROUTINE_ID, routineId);

        return callingIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_ROUTINE_ID, this.routineId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.routineId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_ROUTINE_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.routineId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_ROUTINE_ID);
        }
    }

    private void initializeInjector() {
        this.routineComponent = DaggerRoutineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaRutina.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fFinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaAvisoRutina.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaRutina.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);

        hFinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaAvisoRutina.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour, minute, true);
    }

    @Override
    public RoutineComponent getComponent() {
        return routineComponent;
    }

    @Override
    public void renderRoutine(RoutineModel routine) {
        this.nombreRutina.setText(routine.getName());
        this.lugarRutina.setText(routine.getPlace());
        this.descripcionRutina.setText(routine.getDescription());
        this.fechaRutina.setText(routine.getDateRoutine());
        this.horaRutina.setText(routine.getHourRoutine());
        this.fechaAvisoRutina.setText(routine.getDateAlarm());
        this.horaAvisoRutina.setText(routine.getHourAlarm());
    }

    @OnClick(R.id.fechaRutina)
    public void showPickerFechaR(){
        fIniDatePickerDialog.show();
    }

    @OnClick(R.id.fechaAvisoRutina)
    public void showPickerHoraR(){
        fFinDatePickerDialog.show();
    }

    @OnClick(R.id.horaRutina)
    public void showPickerFechaA(){
        hIniTimePickerDialog.show();
    }

    @OnClick(R.id.horaAvisoRutina)
    public void showPickerHoraA(){
        hFinTimePickerDialog.show();
    }

    @OnClick(R.id.guardarRutina)
    void editClicked(){
        this.routineDetailEditPresenter.editRoutine(this.routineId);
        anadirNotificacion();
    }

    private void anadirNotificacion() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar newCal = Calendar.getInstance();

        if(TextUtils.isEmpty(fechaAvisoRutina.getText()) || TextUtils.isEmpty(horaAvisoRutina.getText())){
            Toast.makeText(this, "No se le notificará la hora de inicio de su rutina, debe completar la fecha y la hora del aviso", Toast.LENGTH_LONG).show();

        }else{
            try {
                newCal.setTime(dateFormat.parse(String.valueOf(fechaAvisoRutina.getText()) + " " + String.valueOf(horaAvisoRutina.getText())));
                newCal.set(Calendar.SECOND,0);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
            scheduleClient.setAlarmForNotification(newCal);
            // Notify the user what they just did
            Toast.makeText(this, "Notificacion guardada para el "+ fechaAvisoRutina.getText() + " a las "+ horaAvisoRutina.getText(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void editRoutine(int routineId) {
        
        Routine routine = new Routine(this.routineId);
        routine.setName(this.nombreRutina.getText().toString());
        routine.setPlace(this.lugarRutina.getText().toString());
        routine.setDescription(this.descripcionRutina.getText().toString());
        routine.setDateRoutine(this.fechaRutina.getText().toString());
        routine.setHourRoutine(this.horaRutina.getText().toString());
        routine.setDateAlarm(this.fechaAvisoRutina.getText().toString());
        routine.setHourAlarm(this.horaAvisoRutina.getText().toString());
        routine.setDuration("20");
        routine.setSatisfaction("50");
        this.routineDetailEditPresenter.saveRoutine(routine);
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
        this.routineDetailEditPresenter.resume();
    }

    @Override
    public void showMessage(String message) {
        this.showToastMessage(message);
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
        this.routineDetailEditPresenter.setView(this);
        this.routineDetailEditPresenter.initialize(this.routineId);
    }

    @Override
    public void goToDetail() {
        this.onBackPressed();
    }
}
