package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;

public class PruebasDetailsActivity extends BaseActivity implements HasComponent<ExamComponent> {

    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private int examId;
    private ExamComponent examComponent;

    public static Intent getCallingIntent(Context context, int examId) {
        Intent callingIntent = new Intent(context, PruebasDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_EXAM_ID, examId);

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

}
