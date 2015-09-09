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
import org.aecc.superdiary.presentation.internal.di.components.RoutineComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerRoutineComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.RoutineModule;
import org.aecc.superdiary.presentation.model.RoutineModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.RoutineListPresenter;
import org.aecc.superdiary.presentation.view.RutinasListView;
import org.aecc.superdiary.presentation.view.adapter.RoutineLayoutManager;
import org.aecc.superdiary.presentation.view.adapter.RoutinesAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RutinasActivity extends DiaryBaseActivity implements HasComponent<RoutineComponent>, RutinasListView{

    public interface RoutineListListener {
        void onRoutineClicked(final RoutineModel routineModel);
    }

    @Inject
    RoutineListPresenter routineListPresenter;

    @InjectView(R.id.recycler_routines)
    RecyclerView recycler_routines;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;
    @InjectView(R.id.botonAnadir)
    Button add;


    private RoutinesAdapter routinesAdapter;

    private RoutineComponent routineComponent;

    private RoutineLayoutManager routineLayoutManager;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(RutinasActivity.this, Rutina.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_rutinas, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ButterKnife.inject(this, view);
        setTitle(titulos[position]);
        this.initializeInjector();
        setupUI();
        //setContentView(R.layout.activity_rutinas);
    }

    private void setupUI() {
        this.routineLayoutManager = new RoutineLayoutManager(this);
        this.recycler_routines.setLayoutManager(routineLayoutManager);
    }

    private void initializeInjector() {
        this.routineComponent = DaggerRoutineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadRoutineList();
    }

    /*@Override public void onRoutineClicked(RoutineModel routineModel) {
        this.navigator.navigateToRoutineDetails(this, routineModel.getRoutineId());
    }*/

    @Override
    public RoutineComponent getComponent() {
        return routineComponent;
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
    public void renderRoutineList(Collection<RoutineModel> routineModelCollection) {
        if (routineModelCollection != null) {
            if (this.routinesAdapter == null) {
                this.routinesAdapter = new RoutinesAdapter(this, routineModelCollection);
            } else {
                this.routinesAdapter.setRoutinesCollection(routineModelCollection);
            }
            this.routinesAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_routines.setAdapter(routinesAdapter);
        }
    }

    @Override
    public void viewRoutine(RoutineModel routineModel) {

        this.onRoutineClicked(routineModel);
        
    }

    @Override
    public void addRoutine() {
        this.navigator.navigateToRoutineAdd(getContext());
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.routineListPresenter.setView(this);
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
        this.routineListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.routineListPresenter.pause();
    }

    private void loadRoutineList() {
        this.routineListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        RutinasActivity.this.loadRoutineList();
    }

    @OnClick(R.id.botonAnadir) void add() {
        RutinasActivity.this.loadRoutineList();
    }

    public void onRoutineClicked(RoutineModel routineModel) {
        this.navigator.navigateToRoutineDetails(this, routineModel.getRoutineId());
    }

    private RoutinesAdapter.OnItemClickListener onItemClickListener =
            new RoutinesAdapter.OnItemClickListener() {
                @Override public void onRoutineItemClicked(RoutineModel routineModel) {
                    if (RutinasActivity.this.routineListPresenter != null && routineModel != null) {
                        RutinasActivity.this.routineListPresenter.onRoutineClicked(routineModel);
                    }
                }
            };

    @OnClick(R.id.botonAnadir)
    public void anadirButtonClicked(){
        this.routineListPresenter.add();
    }
}

