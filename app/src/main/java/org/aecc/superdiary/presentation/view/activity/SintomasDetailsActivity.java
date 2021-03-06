package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerSymptomComponent;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.presenter.SymptomDetailsPresenter;
import org.aecc.superdiary.presentation.view.SintomaDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SintomasDetailsActivity extends BaseActivity implements HasComponent<SymptomComponent>, SintomaDetailView {

    private static final String INTENT_EXTRA_PARAM_SYMPTOM_ID = "org.aecc.INTENT_PARAM_SYMPTOM_ID";
    private static final String INSTANCE_STATE_PARAM_SYMPTOM_ID = "org.aecc.STATE_PARAM_SYMPTOM_ID";

    private int symptomId;
    private SymptomComponent symptomComponent;

    @InjectView(R.id.nombreSintoma)
    TextView nombreSintoma;
    @InjectView(R.id.descripcionSintoma)
    TextView descripcionSintoma;
    @InjectView(R.id.fechaSintoma)
    TextView fechaSintoma;
    @InjectView(R.id.horaSintoma)
    TextView horaSintoma;
    @InjectView(R.id.editarSintoma)
    Button editarSintoma;
    @InjectView(R.id.borrarSintoma)
    Button borrarSintoma;

    @Inject
    public SymptomDetailsPresenter symptomDetailsPresenter;

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
        setContentView(R.layout.activity_sintoma_noedit);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
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

        this.nombreSintoma.setText(symptom.getName());
        this.descripcionSintoma.setText(symptom.getDescription());
        this.fechaSintoma.setText(symptom.getDateSymptom());
        this.horaSintoma.setText(symptom.getHourSymptom());
    }

    @OnClick(R.id.editarSintoma)
    public void editMeeting(){
        this.symptomDetailsPresenter.editSymptom(this.symptomId);
    }

    @Override
    public void editSymptom(int symptomId) {
        this.navigator.navigateToSymptomEdit(getApplicationContext(), symptomId);
    }

    @OnClick(R.id.borrarSintoma)
    public void deleteMeeting(){
        this.symptomDetailsPresenter.deleteSymptom(this.symptomId);
    }

    @Override
    public void deleteSymptom(int symptomId) {
        this.navigator.navigateToSymptomDelete(getApplicationContext(), symptomId);
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
        this.symptomDetailsPresenter.setView(this);
        this.symptomDetailsPresenter.initialize(this.symptomId);
    }
}
