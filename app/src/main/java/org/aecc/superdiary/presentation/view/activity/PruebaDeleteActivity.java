package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.presenter.ExamDetailDeletePresenter;
import org.aecc.superdiary.presentation.view.PruebaDetailDeleteView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PruebaDeleteActivity extends BaseActivity implements PruebaDetailDeleteView, HasComponent<ExamComponent> {

    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private int examId;
    private ExamComponent examComponent;

    //views

    @Inject
    ExamDetailDeletePresenter examDetailDeletePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_prueba_noedit);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    public static Intent getCallingIntent(Context context, int examId) {
        Intent callingIntent = new Intent(context, PruebaDeleteActivity.class);
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

    @Override
    public ExamComponent getComponent() {
        return examComponent;
    }

    @Override
    public void deleteExam(int examId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void renderExam(ExamModel exam) {

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
        this.examDetailDeletePresenter.setView(this);
        this.examDetailDeletePresenter.initialize(this.examId);
    }

    @Override
    public void goToList() {
        this.onBackPressed();
    }
}
