package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMedicineComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.presenter.MedicineDetailDeletePresenter;
import org.aecc.superdiary.presentation.view.MedicamentoDetailDeleteView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MedicamentoDeleteActivity extends BaseActivity implements MedicamentoDetailDeleteView, HasComponent<MedicineComponent> {

    private static final String INTENT_EXTRA_PARAM_MEDICINE_ID = "org.aecc.INTENT_PARAM_MEDICINE_ID";
    private static final String INSTANCE_STATE_PARAM_MEDICINE_ID = "org.aecc.STATE_PARAM_MEDICINE_ID";

    private int medicineId;
    private MedicineComponent medicineComponent;

    //views

    @Inject
    MedicineDetailDeletePresenter medicineDetailDeletePresenter;

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

    public static Intent getCallingIntent(Context context, int medicineId) {
        Intent callingIntent = new Intent(context, MedicamentoDeleteActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_MEDICINE_ID, medicineId);

        return callingIntent;
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
    public void deleteMedicine(int medicineId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void renderMedicine(MedicineModel medicine) {

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
        this.medicineDetailDeletePresenter.setView(this);
        this.medicineDetailDeletePresenter.initialize(this.medicineId);
    }
}
