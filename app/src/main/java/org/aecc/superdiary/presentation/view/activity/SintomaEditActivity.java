package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerSymptomComponent;
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.presenter.SymptomDetailEditPresenter;
import org.aecc.superdiary.presentation.view.SintomaDetailEditView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SintomaEditActivity extends BaseActivity implements SintomaDetailEditView,HasComponent<SymptomComponent>{

    private static final String INTENT_EXTRA_PARAM_SYMPTOM_ID = "org.aecc.INTENT_PARAM_SYMPTOM_ID";
    private static final String INSTANCE_STATE_PARAM_SYMPTOM_ID = "org.aecc.STATE_PARAM_SYMPTOM_ID";

    private int symptomId;
    private SymptomComponent symptomComponent;

    //views

    @Inject
    SymptomDetailEditPresenter symptomDetailEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_rutina_noedit);
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

    @Override
    public SymptomComponent getComponent() {
        return symptomComponent;
    }

    @Override
    public void renderSymptom(SymptomModel symptom) {

    }

    @Override
    public void editSymptom(int symptomId) {

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
