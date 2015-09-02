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
import org.aecc.superdiary.presentation.internal.di.components.MeetingComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerMeetingComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.MeetingModule;
import org.aecc.superdiary.presentation.model.MeetingModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.MeetingListPresenter;
import org.aecc.superdiary.presentation.view.CitasListView;
import org.aecc.superdiary.presentation.view.adapter.MeetingLayoutManager;
import org.aecc.superdiary.presentation.view.adapter.MeetingsAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CitasActivity extends DiaryBaseActivity implements HasComponent<MeetingComponent>, CitasListView{

    public interface MeetingListListener {
        void onMeetingClicked(final MeetingModel meetingModel);
    }

    @Inject
    MeetingListPresenter meetingListPresenter;

    @InjectView(R.id.recycler_meetings)
    RecyclerView recycler_meetings;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private MeetingsAdapter meetingsAdapter;

    private MeetingComponent meetingComponent;

    private MeetingLayoutManager meetingLayoutManager;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(CitasActivity.this, Personaje.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_citas, frameLayout);
        mDrawerList.setItemChecked(position, true);
        ButterKnife.inject(this, view);
        setTitle(titulos[position]);
        this.initializeInjector();
        setupUI();
        //setContentView(R.layout.activity_citas);
    }

    private void setupUI() {
        this.meetingLayoutManager = new MeetingLayoutManager(this);
        this.recycler_meetings.setLayoutManager(meetingLayoutManager);
    }

    private void initializeInjector() {
        this.meetingComponent = DaggerMeetingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadMeetingList();
    }

    /*@Override public void onMeetingClicked(MeetingModel meetingModel) {
        this.navigator.navigateToContacDetails(this, meetingModel.getMeetingId());
    }*/

    @Override
    public MeetingComponent getComponent() {
        return meetingComponent;
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
    public void renderMeetingList(Collection<MeetingModel> meetingModelCollection) {
        if (meetingModelCollection != null) {
            if (this.meetingsAdapter == null) {
                this.meetingsAdapter = new MeetingsAdapter(this, meetingModelCollection);
            } else {
                this.meetingsAdapter.setMeetingsCollection(meetingModelCollection);
            }
            this.meetingsAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_meetings.setAdapter(meetingsAdapter);
        }
    }

    @Override
    public void viewMeeting(MeetingModel meetingModel) {

        this.onMeetingClicked(meetingModel);
        
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.meetingListPresenter.setView(this);
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
        this.meetingListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.meetingListPresenter.pause();
    }

    private void loadMeetingList() {
        this.meetingListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        CitasActivity.this.loadMeetingList();
    }

    public void onMeetingClicked(MeetingModel meetingModel) {
        this.navigator.navigateToContacDetails(this, meetingModel.getMeetingId());
    }

    private MeetingsAdapter.OnItemClickListener onItemClickListener =
            new MeetingsAdapter.OnItemClickListener() {
                @Override public void onMeetingItemClicked(MeetingModel meetingModel) {
                    if (CitasActivity.this.meetingListPresenter != null && meetingModel != null) {
                        CitasActivity.this.meetingListPresenter.onMeetingClicked(meetingModel);
                    }
                }
            };
}

