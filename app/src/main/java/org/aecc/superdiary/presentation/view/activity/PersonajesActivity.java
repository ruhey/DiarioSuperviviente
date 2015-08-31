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
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerApplicationComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerContactComponent;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.internal.di.modules.ContactModule;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.navigator.Navigator;
import org.aecc.superdiary.presentation.presenter.ContactListPresenter;
import org.aecc.superdiary.presentation.view.PersonajesListView;
import org.aecc.superdiary.presentation.view.adapter.ContactsAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;


public class PersonajesActivity extends DiaryBaseActivity implements HasComponent<ContactComponent>, PersonajesListView{

    public interface ContactListListener {
        void onContactClicked(final ContactModel contactModel);
    }

    @Inject
    ContactListPresenter contactListPresenter;

    @InjectView(R.id.recycler_contacts)
    RecyclerView recycler_contacts;
    @InjectView(R.id.rl_progress)
    RelativeLayout rl_progress;
    @InjectView(R.id.rl_retry) RelativeLayout rl_retry;
    @InjectView(R.id.bt_retry)
    Button bt_retry;

    private ContactsAdapter contactsAdapter;

    private ContactComponent contactComponent;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(PersonajesActivity.this, Personaje.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_personajes, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        this.initializeInjector();
        //setContentView(R.layout.activity_personajes);
    }

    private void initializeInjector() {
        this.contactComponent = DaggerContactComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.initialize();
        this.loadContactList();
    }

    /*@Override public void onContactClicked(ContactModel contactModel) {
        this.navigator.navigateToContacDetails(this, contactModel.getContactId());
    }*/

    @Override
    public ContactComponent getComponent() {
        return contactComponent;
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
    public void renderContactList(Collection<ContactModel> contactModelCollection) {
        if (contactModelCollection != null) {
            if (this.contactsAdapter == null) {
                this.contactsAdapter = new ContactsAdapter(this, contactModelCollection);
            } else {
                this.contactsAdapter.setContactsCollection(contactModelCollection);
            }
            this.contactsAdapter.setOnItemClickListener(onItemClickListener);
            this.recycler_contacts.setAdapter(contactsAdapter);
        }
    }

    @Override
    public void viewContact(ContactModel contactModel) {

        this.onContactClicked(contactModel);
        
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.contactListPresenter.setView(this);
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
        this.contactListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.contactListPresenter.pause();
    }

    private void loadContactList() {
        this.contactListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        PersonajesActivity.this.loadContactList();
    }

    public void onContactClicked(ContactModel contactModel) {
        this.navigator.navigateToContacDetails(this, contactModel.getContactId());
    }

    private ContactsAdapter.OnItemClickListener onItemClickListener =
            new ContactsAdapter.OnItemClickListener() {
                @Override public void onContactItemClicked(ContactModel contactModel) {
                    if (PersonajesActivity.this.contactListPresenter != null && contactModel != null) {
                        PersonajesActivity.this.contactListPresenter.onContactClicked(contactModel);
                    }
                }
            };
}

