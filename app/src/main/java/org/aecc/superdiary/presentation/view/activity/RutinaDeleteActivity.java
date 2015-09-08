package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerRoutineComponent;
import org.aecc.superdiary.presentation.internal.di.components.RoutineComponent;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.presenter.RoutineDetailDeletePresenter;
import org.aecc.superdiary.presentation.view.RutinaDetailDeleteView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RutinaDeleteActivity extends BaseActivity implements RutinaDetailDeleteView, HasComponent<RoutineComponent> {

    private static final String INTENT_EXTRA_PARAM_ROUTINE_ID = "org.aecc.INTENT_PARAM_ROUTINE_ID";
    private static final String INSTANCE_STATE_PARAM_ROUTINE_ID = "org.aecc.STATE_PARAM_ROUTINE_ID";

    private int routineId;
    private RoutineComponent routineComponent;

    //views

    @Inject
    RoutineDetailDeletePresenter routineDetailDeletePresenter;

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

    public static Intent getCallingIntent(Context context, int routineId) {
        Intent callingIntent = new Intent(context, RutinaDeleteActivity.class);
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

    @Override
    public RoutineComponent getComponent() {
        return routineComponent;
    }

    @Override
    public void deleteRoutine(int routineId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void renderRoutine(RoutineModel routine) {

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
        this.routineDetailDeletePresenter.setView(this);
        this.routineDetailDeletePresenter.initialize(this.routineId);
    }
}
