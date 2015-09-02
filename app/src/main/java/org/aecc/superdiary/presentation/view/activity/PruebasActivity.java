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
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.ExamModule;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.ExamListPresenter;
import org.aecc.superdiary.presentation.view.PruebasListView;
import org.aecc.superdiary.presentation.view.adapter.ExamLayoutManager;
import org.aecc.superdiary.presentation.view.adapter.ExamsAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class PruebasActivity extends DiaryBaseActivity implements HasComponent<ExamComponent>, PruebasListView{

    public interface ExamListListener {
        void onExamClicked(final ExamModel examModel);
    }

    @Inject
    ExamListPresenter examListPresenter;

    @InjectView(R.id.recycler_exams)
    RecyclerView recycler_exams;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private ExamsAdapter examsAdapter;

    private ExamComponent examComponent;

    private ExamLayoutManager examLayoutManager;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(PruebasActivity.this, Prueba.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_pruebas, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ButterKnife.inject(this, view);
        setTitle(titulos[position]);
        this.initializeInjector();
        setupUI();
        //setContentView(R.layout.activity_pruebas);
    }

    private void setupUI() {
        this.examLayoutManager = new ExamLayoutManager(this);
        this.recycler_exams.setLayoutManager(examLayoutManager);
    }

    private void initializeInjector() {
        this.examComponent = DaggerExamComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadExamList();
    }

    /*@Override public void onExamClicked(ExamModel examModel) {
        this.navigator.navigateToContacDetails(this, examModel.getExamId());
    }*/

    @Override
    public ExamComponent getComponent() {
        return examComponent;
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
    public void renderExamList(Collection<ExamModel> examModelCollection) {
        if (examModelCollection != null) {
            if (this.examsAdapter == null) {
                this.examsAdapter = new ExamsAdapter(this, examModelCollection);
            } else {
                this.examsAdapter.setExamsCollection(examModelCollection);
            }
            this.examsAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_exams.setAdapter(examsAdapter);
        }
    }

    @Override
    public void viewExam(ExamModel examModel) {

        this.onExamClicked(examModel);
        
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.examListPresenter.setView(this);
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
        this.examListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.examListPresenter.pause();
    }

    private void loadExamList() {
        this.examListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        PruebasActivity.this.loadExamList();
    }

    public void onExamClicked(ExamModel examModel) {
        this.navigator.navigateToContacDetails(this, examModel.getExamId());
    }

    private ExamsAdapter.OnItemClickListener onItemClickListener =
            new ExamsAdapter.OnItemClickListener() {
                @Override public void onExamItemClicked(ExamModel examModel) {
                    if (PruebasActivity.this.examListPresenter != null && examModel != null) {
                        PruebasActivity.this.examListPresenter.onExamClicked(examModel);
                    }
                }
            };
}

