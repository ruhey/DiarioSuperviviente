package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerSymptomComponent;

public class SintomasDetailsActivity extends BaseActivity implements HasComponent<SymptomComponent> {

    private static final String INTENT_EXTRA_PARAM_SYMPTOM_ID = "org.aecc.INTENT_PARAM_SYMPTOM_ID";
    private static final String INSTANCE_STATE_PARAM_SYMPTOM_ID = "org.aecc.STATE_PARAM_SYMPTOM_ID";

    private int symptomId;
    private SymptomComponent symptomComponent;

    public static Intent getCallingIntent(Context context, int symptomId) {
        Intent callingIntent = new Intent(context, SintomasDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_SYMPTOM_ID, symptomId);

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

    @Override
    public SymptomComponent getComponent() {
        return symptomComponent;
    }

}
