package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMedicineComponent;

public class MedicamentosDetailsActivity extends BaseActivity implements HasComponent<MedicineComponent> {

    private static final String INTENT_EXTRA_PARAM_MEDICINE_ID = "org.aecc.INTENT_PARAM_MEDICINE_ID";
    private static final String INSTANCE_STATE_PARAM_MEDICINE_ID = "org.aecc.STATE_PARAM_MEDICINE_ID";

    private int medicineId;
    private MedicineComponent medicineComponent;

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

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
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

}
