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
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerSymptomComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.SymptomModule;
import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.SymptomListPresenter;
import org.aecc.superdiary.presentation.view.SintomasListView;
import org.aecc.superdiary.presentation.view.adapter.SymptomLayoutManager;
import org.aecc.superdiary.presentation.view.adapter.SymptomsAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SintomasActivity extends DiaryBaseActivity implements HasComponent<SymptomComponent>, SintomasListView{

    public interface SymptomListListener {
        void onSymptomClicked(final SymptomModel symptomModel);
    }

    @Inject
    SymptomListPresenter symptomListPresenter;

    @InjectView(R.id.recycler_symptoms)
    RecyclerView recycler_symptoms;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private SymptomsAdapter symptomsAdapter;

    private SymptomComponent symptomComponent;

    private SymptomLayoutManager symptomLayoutManager;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(SintomasActivity.this, Sintoma.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_sintomas, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ButterKnife.inject(this, view);
        setTitle(titulos[position]);
        this.initializeInjector();
        setupUI();
        //setContentView(R.layout.activity_sintomas);
    }

    private void setupUI() {
        this.symptomLayoutManager = new SymptomLayoutManager(this);
        this.recycler_symptoms.setLayoutManager(symptomLayoutManager);
    }

    private void initializeInjector() {
        this.symptomComponent = DaggerSymptomComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadSymptomList();
    }

    /*@Override public void onSymptomClicked(SymptomModel symptomModel) {
        this.navigator.navigateToSymptomDetails(this, symptomModel.getSymptomId());
    }*/

    @Override
    public SymptomComponent getComponent() {
        return symptomComponent;
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
    public void renderSymptomList(Collection<SymptomModel> symptomModelCollection) {
        if (symptomModelCollection != null) {
            if (this.symptomsAdapter == null) {
                this.symptomsAdapter = new SymptomsAdapter(this, symptomModelCollection);
            } else {
                this.symptomsAdapter.setSymptomsCollection(symptomModelCollection);
            }
            this.symptomsAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_symptoms.setAdapter(symptomsAdapter);
        }
    }

    @Override
    public void viewSymptom(SymptomModel symptomModel) {

        this.onSymptomClicked(symptomModel);
        
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.symptomListPresenter.setView(this);
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
        this.symptomListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.symptomListPresenter.pause();
    }

    private void loadSymptomList() {
        this.symptomListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        SintomasActivity.this.loadSymptomList();
    }

    public void onSymptomClicked(SymptomModel symptomModel) {
        this.navigator.navigateToSymptomDetails(this, symptomModel.getSymptomId());
    }

    private SymptomsAdapter.OnItemClickListener onItemClickListener =
            new SymptomsAdapter.OnItemClickListener() {
                @Override public void onSymptomItemClicked(SymptomModel symptomModel) {
                    if (SintomasActivity.this.symptomListPresenter != null && symptomModel != null) {
                        SintomasActivity.this.symptomListPresenter.onSymptomClicked(symptomModel);
                    }
                }
            };
}

