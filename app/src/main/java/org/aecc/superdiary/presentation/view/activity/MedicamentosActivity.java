package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMedicineComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.MedicineModule;
import org.aecc.superdiary.presentation.model.MedicineModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.MedicineListPresenter;
import org.aecc.superdiary.presentation.view.MedicamentosListView;
import org.aecc.superdiary.presentation.view.adapter.MedicineLayoutManager;
import org.aecc.superdiary.presentation.view.adapter.MedicinesAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MedicamentosActivity extends DiaryBaseActivity implements HasComponent<MedicineComponent>, MedicamentosListView{

    public interface MedicineListListener {
        void onMedicineClicked(final MedicineModel medicineModel);
    }

    @Inject
    MedicineListPresenter medicineListPresenter;

    @InjectView(R.id.recycler_medicines)
    RecyclerView recycler_medicines;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private MedicinesAdapter medicinesAdapter;

    private MedicineComponent medicineComponent;

    private MedicineLayoutManager medicineLayoutManager;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(MedicamentosActivity.this, Medicamento.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_medicamentos, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ButterKnife.inject(this, view);
        setTitle(titulos[position]);
        this.initializeInjector();
        setupUI();
        //setContentView(R.layout.activity_medicamentos);
    }

    private void setupUI() {
        this.medicineLayoutManager = new MedicineLayoutManager(this);
        this.recycler_medicines.setLayoutManager(medicineLayoutManager);
    }

    private void initializeInjector() {
        this.medicineComponent = DaggerMedicineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadMedicineList();
    }

    /*@Override public void onMedicineClicked(MedicineModel medicineModel) {
        this.navigator.navigateToContacDetails(this, medicineModel.getMedicineId());
    }*/

    @Override
    public MedicineComponent getComponent() {
        return medicineComponent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    //Commit inicial para ver si funciona
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int id2 = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void renderMedicineList(Collection<MedicineModel> medicineModelCollection) {
        if (medicineModelCollection != null) {
            if (this.medicinesAdapter == null) {
                this.medicinesAdapter = new MedicinesAdapter(this, medicineModelCollection);
            } else {
                this.medicinesAdapter.setMedicinesCollection(medicineModelCollection);
            }
            this.medicinesAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_medicines.setAdapter(medicinesAdapter);
        }
    }

    @Override
    public void viewMedicine(MedicineModel medicineModel) {

        this.onMedicineClicked(medicineModel);
        
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.medicineListPresenter.setView(this);
    }

    @Override public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.setProgressBarIndeterminateVisibility(true);
    }

    @Override public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.setProgressBarIndeterminateVisibility(false);
    }

    @Override public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override public void onResume() {
        super.onResume();
        this.medicineListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.medicineListPresenter.pause();
    }

    private void loadMedicineList() {
        this.medicineListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        MedicamentosActivity.this.loadMedicineList();
    }

    public void onMedicineClicked(MedicineModel medicineModel) {
        this.navigator.navigateToContacDetails(this, medicineModel.getMedicineId());
    }

    private MedicinesAdapter.OnItemClickListener onItemClickListener =
            new MedicinesAdapter.OnItemClickListener() {
                @Override public void onMedicineItemClicked(MedicineModel medicineModel) {
                    if (MedicamentosActivity.this.medicineListPresenter != null && medicineModel != null) {
                        MedicamentosActivity.this.medicineListPresenter.onMedicineClicked(medicineModel);
                    }
                }
            };
}

