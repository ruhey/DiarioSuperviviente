package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerContactComponent;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.presenter.ContactsDetailsNoEditPresenter;
import org.aecc.superdiary.presentation.view.PersonajeDetailNoEditView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonajeDetailsNoEditActivity extends BaseActivity implements HasComponent<ContactComponent>, PersonajeDetailNoEditView {
    private static final String INTENT_EXTRA_PARAM_CONTACT_ID = "org.aecc.INTENT_PARAM_CONTACT_ID";
    private static final String INSTANCE_STATE_PARAM_CONTACT_ID = "org.aecc.STATE_PARAM_CONTACT_ID";

    private int contactId;
    private ContactComponent contactComponent;
    @InjectView(R.id.nombre_no_edit)
    TextView nombreInsert;
    @InjectView(R.id.apellidos_no_edit)
    TextView apellidosInsert;
    @InjectView(R.id.direccion_no_edit)
    TextView direccionInsert;
    @InjectView(R.id.telefono_no_edit)
    TextView telefonoInsert;
    @InjectView(R.id.email_no_edit)
    TextView emailInsert;
    @InjectView(R.id.editarPersonaje)
    Button editContact;
    @InjectView(R.id.borrarPersonaje)
    Button deleteContact;
    @Inject
    ContactsDetailsNoEditPresenter contactsDetailsNoEditPresenter;

    public static Intent getCallingIntent(Context context, int contactId) {
        Intent callingIntent = new Intent(context, PersonajeDetailsNoEditActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CONTACT_ID, contactId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_personaje_noedit);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_CONTACT_ID, this.contactId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.contactId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CONTACT_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));

        } else {
            this.contactId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CONTACT_ID);
        }
        Log.e("EEEOOO", "El contactId es: " + this.contactId);
    }

    private void initializeInjector() {
        this.contactComponent = DaggerContactComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ContactComponent getComponent() {
        return contactComponent;
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

    @Override public void onResume() {
        super.onResume();
        this.contactsDetailsNoEditPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.contactsDetailsNoEditPresenter.pause();
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.contactsDetailsNoEditPresenter.setView(this);
        this.contactsDetailsNoEditPresenter.initialize(this.contactId);
    }

    @Override public void renderContact(ContactModel contact) {
        if (contact != null) {
            this.nombreInsert.setText(contact.getName());
            this.apellidosInsert.setText(contact.getSurname());
            this.direccionInsert.setText(contact.getCategory());
            this.telefonoInsert.setText(contact.getPhone());
            this.emailInsert.setText(contact.getEmail());
        }
    }

    @OnClick(R.id.editarPersonaje) void onButtonEditClick() {
        this.contactsDetailsNoEditPresenter.editContact(this.contactId);
    }

    @OnClick(R.id.borrarPersonaje) void onButtonDeleteClick() {
        this.contactsDetailsNoEditPresenter.deleteContact(this.contactId);
    }

    @Override
    public void editContact(int contactId) {
        this.navigator.navigateToContacDetailsEdit(this, contactId);
    }

    @Override
    public void deleteContact(int contactId) {
        this.navigator.navigateToContacDetailsDelete(this, contactId);
    }
}
