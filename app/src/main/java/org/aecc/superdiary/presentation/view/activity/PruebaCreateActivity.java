package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.presenter.ExamDetailCreatePresenter;
import org.aecc.superdiary.presentation.view.PruebaDetailCreateView;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PruebaCreateActivity extends BaseActivity implements PruebaDetailCreateView, HasComponent<ExamComponent> {
    
    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private ExamComponent examComponent;

    @Inject
    ExamDetailCreatePresenter examDetailCreatePresenter;


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
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, PruebaCreateActivity.class);
        return callingIntent;
    }


    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //this.examId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_EXAM_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.examId));
        } else {
            //this.examId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_ROUTINE_ID);
        }
    }

    private void initializeInjector() {
        this.examComponent = DaggerExamComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @OnClick(R.id.guardarPrueba)
    public void editPrueba(){
        Exam exam = new Exam(55);
        exam.setName(this.nombrePrueba.getText().toString());
        exam.setDescription(this.descripcionPrueba.getText().toString());
        exam.setDateExam(this.fechaPrueba.getText().toString());
        exam.setHourExam(this.horaPrueba.getText().toString());
        this.examDetailCreatePresenter.createExam(exam);
    }


    @Override public void onResume() {
        super.onResume();
        this.examDetailCreatePresenter.resume();
    }


    @Override
    public ExamComponent getComponent() {
        return null;
    }

    @Override
    public void createExam(int examId) {

    }

    @Override
    public void showMessage(String message) {

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

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.examDetailCreatePresenter.setView(this);
        this.examDetailCreatePresenter.initialize(33);
    }
}
