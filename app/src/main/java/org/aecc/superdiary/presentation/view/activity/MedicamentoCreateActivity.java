package org.aecc.superdiary.presentation.view.activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Medicine;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMedicineComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.presenter.MedicineDetailCreatePresenter;
import org.aecc.superdiary.presentation.view.MedicamentoDetailCreateView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MedicamentoCreateActivity extends BaseActivity implements MedicamentoDetailCreateView, HasComponent<MedicineComponent> {

    private static final String INTENT_EXTRA_PARAM_MEDICINE_ID = "org.aecc.INTENT_PARAM_MEDICINE_ID";
    private static final String INSTANCE_STATE_PARAM_MEDICINE_ID = "org.aecc.STATE_PARAM_MEDICINE_ID";

    private int medicineId;
    private MedicineComponent medicineComponent;

    @InjectView(R.id.nombreMedic)
    EditText nombreMedic;
    @InjectView(R.id.descripcionMedic)
    EditText descripcionMedic;
    @InjectView(R.id.fechaInicMedic)
    EditText fechaInicMedic;
    @InjectView(R.id.horaIniMedic)
    EditText horaIniMedic;
    @InjectView(R.id.fechaFinMedic)
    EditText fechaFinMedic;
    @InjectView(R.id.horaFinMedic)
    EditText horaFinMedic;
    //@InjectView(R.id.intervaloMedic)
    //EditText intervaloMedic;
    //@InjectView(R.id.fotoMedic)
    //EditText fotoMedic;
    @InjectView(R.id.guardarMedicamento)
    Button guardarMedicamento;

    private DatePickerDialog fIniDatePickerDialog;
    private DatePickerDialog fFinDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;
    private TimePickerDialog hFinTimePickerDialog;

    private ScheduleClient scheduleClient;
    private final String TIPO_NOTIFICACION = "M";

    private SimpleDateFormat dateFormatter;

    @Inject
    MedicineDetailCreatePresenter medicineDetailCreatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_medicamento);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this, TIPO_NOTIFICACION);
        scheduleClient.doBindService();
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaInicMedic.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fFinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaFinMedic.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaIniMedic.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);

        hFinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaFinMedic.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);
    }


    @Override
    public MedicineComponent getComponent() {
        return medicineComponent;
    }

    @OnClick(R.id.fechaInicMedic)
    public void showPickerFechaR() {
        fIniDatePickerDialog.show();
    }

    @OnClick(R.id.fechaFinMedic)
    public void showPickerHoraR() {
        fFinDatePickerDialog.show();
    }

    @OnClick(R.id.horaIniMedic)
    public void showPickerFechaA() {
        hIniTimePickerDialog.show();
    }

    @OnClick(R.id.horaFinMedic)
    public void showPickerHoraA() {
        hFinTimePickerDialog.show();
    }

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, MedicamentoCreateActivity.class);
        return callingIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            //outState.putInt(INSTANCE_STATE_PARAM_ROUTINE_ID, this.examId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //this.examId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_ROUTINE_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            //this.examId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_ROUTINE_ID);
        }
    }

    private void initializeInjector() {
        this.medicineComponent = DaggerMedicineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @OnClick(R.id.guardarMedicamento)
    public void editMedicine(int medicineId) {
        Medicine medicine = new Medicine(55);
        medicine.setName(this.nombreMedic.getText().toString());
        medicine.setDescription(this.descripcionMedic.getText().toString());
        medicine.setFirstDay(this.fechaInicMedic.getText().toString());
        medicine.setFirstHour(this.horaIniMedic.getText().toString());
        medicine.setLastDay(this.fechaFinMedic.getText().toString());
        medicine.setLastHour(this.horaFinMedic.getText().toString());
        medicine.setInterval("20");
        //medicine.setImage(this.horaFinMedic.getText().toString());
        this.medicineDetailCreatePresenter.createMedicine(medicine);
    }

    @Override
    public void createMedicine(int medicineId) {
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
        this.medicineDetailCreatePresenter.setView(this);
        this.medicineDetailCreatePresenter.initialize(33);
    }
}
