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
import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.presenter.ExamDetailEditPresenter;
import org.aecc.superdiary.presentation.view.PruebaDetailEditView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PruebaEditActivity extends BaseActivity implements PruebaDetailEditView,HasComponent<ExamComponent>{

    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private int examId;
    private ExamComponent examComponent;

    @InjectView(R.id.nombrePrueba)
    EditText nombrePrueba;
    @InjectView(R.id.descripcionPrueba)
    EditText descripcionPrueba;
    @InjectView(R.id.fechaPrueba)
    EditText fechaPrueba;
    @InjectView(R.id.horaPrueba)
    EditText horaPrueba;
    @InjectView(R.id.guardarPrueba)
    Button guardarPrueba;

    private DatePickerDialog fIniDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    
    @Inject
    ExamDetailEditPresenter examDetailEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    public static Intent getCallingIntent(Context context, int examId) {
        Intent callingIntent = new Intent(context, PruebaEditActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_EXAM_ID, examId);

        return callingIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_EXAM_ID, this.examId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.examId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_EXAM_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.examId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_EXAM_ID);
        }
    }

    private void initializeInjector() {
        this.examComponent = DaggerExamComponent.builder()
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
                fechaPrueba.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaPrueba.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);
    }

    @Override
    public ExamComponent getComponent() {
        return examComponent;
    }

    @Override
    public void renderExam(ExamModel exam) {
        this.nombrePrueba.setText(exam.getName());
        this.descripcionPrueba.setText(exam.getDescription());
        this.fechaPrueba.setText(exam.getDateExam());
        this.horaPrueba.setText(exam.getHourExam());
    }

    @OnClick(R.id.fechaPrueba)
    public void showPickerFechaR(){
        fIniDatePickerDialog.show();
    }

    @OnClick(R.id.horaPrueba)
    public void showPickerFechaA(){
        hIniTimePickerDialog.show();
    }

    @OnClick(R.id.guardarPrueba)
    void editClicked(){
        this.examDetailEditPresenter.editExam(this.examId);
    }

    @Override
    public void editExam(int examId) {
        Exam exam = new Exam(this.examId);
        exam.setName(this.nombrePrueba.getText().toString());
        exam.setDescription(this.descripcionPrueba.getText().toString());
        exam.setDateExam(this.fechaPrueba.getText().toString());
        exam.setHourExam(this.horaPrueba.getText().toString());
        this.examDetailEditPresenter.saveExam(exam);
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
        this.examDetailEditPresenter.setView(this);
        this.examDetailEditPresenter.initialize(this.examId);
    }
}
