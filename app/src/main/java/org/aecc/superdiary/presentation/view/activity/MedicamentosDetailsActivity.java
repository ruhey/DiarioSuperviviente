package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMedicineComponent;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.presenter.MedicineDetailsPresenter;
import org.aecc.superdiary.presentation.view.MedicamentoDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MedicamentosDetailsActivity extends BaseActivity implements HasComponent<MedicineComponent>, MedicamentoDetailView {

    private static final String INTENT_EXTRA_PARAM_MEDICINE_ID = "org.aecc.INTENT_PARAM_MEDICINE_ID";
    private static final String INSTANCE_STATE_PARAM_MEDICINE_ID = "org.aecc.STATE_PARAM_MEDICINE_ID";

    private int medicineId;
    private MedicineComponent medicineComponent;

    @InjectView(R.id.nombreMedic)
    TextView nombreMedic;
    @InjectView(R.id.descripcionMedic)
    TextView descripcionMedic;
    @InjectView(R.id.fechaInicMedic)
    TextView fechaInicMedic;
    @InjectView(R.id.fechaFinMedic)
    TextView fechaFinMedic;
    @InjectView(R.id.horaIniMedic)
    TextView horaIniMedic;
    @InjectView(R.id.horaFinMedic)
    TextView horaFinMedic;
    @InjectView(R.id.intervaloMedic)
    SeekBar intervaloMedic;
    @InjectView(R.id.editarMedicamento)
    Button editarMedicamento;
    @InjectView(R.id.borrarMedicamento)
    Button borrarMedicamento;

    @Inject
    public MedicineDetailsPresenter medicineDetailsPresenter;

    public static Intent getCallingIntent(Context context, int medicineId) {
        Intent callingIntent = new Intent(context, MedicamentosDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MEDICINE_ID, medicineId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_medicamento_noedit);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_MEDICINE_ID, this.medicineId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.medicineId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_MEDICINE_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.medicineId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_MEDICINE_ID);
        }
    }

    private void initializeInjector() {
        this.medicineComponent = DaggerMedicineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MedicineComponent getComponent() {
        return medicineComponent;
    }

    @Override
    public void renderMedicine(MedicineModel medicine) {

        this.nombreMedic.setText(medicine.getName());
        this.descripcionMedic.setText(medicine.getDescription());
        this.fechaInicMedic.setText(medicine.getFirstDay());
        this.horaIniMedic.setText(medicine.getFirstHour());
        this.fechaFinMedic.setText(medicine.getLastDay());
        this.horaFinMedic.setText(medicine.getLastHour());
        this.intervaloMedic.setProgress(Integer.parseInt(medicine.getInterval()));
    }

    @OnClick(R.id.editarMedicamento)
    public void editMeeting(){
        this.medicineDetailsPresenter.editMedicine(this.medicineId);
    }

    @Override
    public void editMedicine(int medicineId) {
        this.navigator.navigateToMedicineEdit(getApplicationContext(), medicineId);
    }

    @OnClick(R.id.borrarMedicamento)
    public void deleteMeeting(){
        this.medicineDetailsPresenter.deleteMedicine(this.medicineId);
    }

    @Override
    public void deleteMedicine(int medicineId) {
        this.navigator.navigateToMedicineDelete(getApplicationContext(), medicineId);
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
        this.medicineDetailsPresenter.setView(this);
        this.medicineDetailsPresenter.initialize(this.medicineId);
    }
}
