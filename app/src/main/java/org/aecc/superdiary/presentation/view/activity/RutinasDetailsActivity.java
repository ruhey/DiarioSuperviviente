package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.RoutineComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerRoutineComponent;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.presenter.RoutineDetailsPresenter;
import org.aecc.superdiary.presentation.view.RutinaDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RutinasDetailsActivity extends BaseActivity implements HasComponent<RoutineComponent>, RutinaDetailView {

    private static final String INTENT_EXTRA_PARAM_ROUTINE_ID = "org.aecc.INTENT_PARAM_ROUTINE_ID";
    private static final String INSTANCE_STATE_PARAM_ROUTINE_ID = "org.aecc.STATE_PARAM_ROUTINE_ID";

    private int routineId;
    private RoutineComponent routineComponent;

    @InjectView(R.id.nombreRutina)
    TextView nombreRutina;
    @InjectView(R.id.lugarRutina)
    TextView lugarRutina;
    @InjectView(R.id.descripcionRutina)
    TextView descripcionRutina;
    @InjectView(R.id.fechaRutina)
    TextView fechaRutina;
    @InjectView(R.id.fechaAvisoRutina)
    TextView fechaAvisoRutina;
    @InjectView(R.id.horaRutina)
    TextView horaRutina;
    @InjectView(R.id.horaAvisoRutina)
    TextView horaAvisoRutina;
    @InjectView(R.id.editarRutina)
    Button editarRutina;
    @InjectView(R.id.borrarRutina)
    Button borrarRutina;

    @Inject
    public RoutineDetailsPresenter routineDetailsPresenter;

    public static Intent getCallingIntent(Context context, int routineId) {
        Intent callingIntent = new Intent(context, RutinasDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ROUTINE_ID, routineId);

        return callingIntent;
    }

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
    public void renderRoutine(RoutineModel routine) {

        this.nombreRutina.setText(routine.getName());
        this.lugarRutina.setText(routine.getPlace());
        this.descripcionRutina.setText(routine.getDescription());
        this.fechaRutina.setText(routine.getDateRoutine());
        this.horaRutina.setText(routine.getHourRoutine());
        this.fechaAvisoRutina.setText(routine.getDateAlarm());
        this.horaAvisoRutina.setText(routine.getHourAlarm());
    }

    @OnClick(R.id.editarRutina)
    public void editMeeting(){
        this.routineDetailsPresenter.editRountine(this.routineId);
    }

    @Override
    public void editRoutine(int routineId) {
        this.navigator.navigateToRoutineEdit(getApplicationContext(), routineId);
    }

    @OnClick(R.id.borrarRutina)
    public void deleteMeeting(){
        this.routineDetailsPresenter.deleteRoutine(this.routineId);
    }

    @Override
    public void deleteRoutine(int routineId) {
        this.navigator.navigateToRoutineDelete(getApplicationContext(), routineId);
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
        this.routineDetailsPresenter.setView(this);
        this.routineDetailsPresenter.initialize(this.routineId);
    }
}
