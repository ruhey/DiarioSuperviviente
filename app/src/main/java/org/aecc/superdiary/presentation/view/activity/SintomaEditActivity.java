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
import org.aecc.superdiary.domain.Symptom;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerSymptomComponent;
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.presenter.SymptomDetailEditPresenter;
import org.aecc.superdiary.presentation.view.SintomaDetailEditView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SintomaEditActivity extends BaseActivity implements SintomaDetailEditView,HasComponent<SymptomComponent>{

    private static final String INTENT_EXTRA_PARAM_SYMPTOM_ID = "org.aecc.INTENT_PARAM_SYMPTOM_ID";
    private static final String INSTANCE_STATE_PARAM_SYMPTOM_ID = "org.aecc.STATE_PARAM_SYMPTOM_ID";

    private int symptomId;
    private SymptomComponent symptomComponent;

    @InjectView(R.id.nombreSintoma)
    EditText nombreSintoma;
    @InjectView(R.id.descripcionSintoma)
    EditText descripcionSintoma;
    @InjectView(R.id.fechaSintoma)
    EditText fechaSintoma;
    @InjectView(R.id.horaSintoma)
    EditText horaSintoma;
    @InjectView(R.id.guardarSintoma)
    Button guardarSintoma;

    private DatePickerDialog fIniDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Inject
    SymptomDetailEditPresenter symptomDetailEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintoma);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    public static Intent getCallingIntent(Context context, int symptomId) {
        Intent callingIntent = new Intent(context, SintomaEditActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_SYMPTOM_ID, symptomId);

        return callingIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_SYMPTOM_ID, this.symptomId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.symptomId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_SYMPTOM_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.symptomId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_SYMPTOM_ID);
        }
    }

    private void initializeInjector() {
        this.symptomComponent = DaggerSymptomComponent.builder()
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
                fechaSintoma.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaSintoma.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);
    }

    @Override
    public SymptomComponent getComponent() {
        return symptomComponent;
    }

    @Override
    public void renderSymptom(SymptomModel symptom) {
        this.nombreSintoma.setText(symptom.getName());
        this.descripcionSintoma.setText(symptom.getDescription());
        this.fechaSintoma.setText(symptom.getDateSymptom());
        this.horaSintoma.setText(symptom.getHourSymptom());
    }

    @OnClick(R.id.fechaSintoma)
    public void showPickerFechaR(){
        fIniDatePickerDialog.show();
    }

    @OnClick(R.id.horaSintoma)
    public void showPickerFechaA(){
        hIniTimePickerDialog.show();
    }

    @OnClick(R.id.guardarSintoma)
    void editClicked(){
        this.symptomDetailEditPresenter.editSymptom(this.symptomId);
    }

    @Override
    public void editSymptom(int symptomId) {
        Symptom symptom = new Symptom(this.symptomId);
        symptom.setName(this.nombreSintoma.getText().toString());
        symptom.setDescription(this.descripcionSintoma.getText().toString());
        symptom.setDateSymptom(this.fechaSintoma.getText().toString());
        symptom.setHourSymptom(this.horaSintoma.getText().toString());
        this.symptomDetailEditPresenter.saveSymptom(symptom);
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
        this.symptomDetailEditPresenter.setView(this);
        this.symptomDetailEditPresenter.initialize(this.symptomId);
    }
}
