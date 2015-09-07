package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerContactComponent;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.presenter.ContactDetailsPresenter;
import org.aecc.superdiary.presentation.view.PersonajeDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonajesDetailsActivity extends BaseActivity implements HasComponent<ContactComponent>, PersonajeDetailView {

    private static final String INTENT_EXTRA_PARAM_CONTACT_ID = "org.aecc.INTENT_PARAM_CONTACT_ID";
    private static final String INSTANCE_STATE_PARAM_CONTACT_ID = "org.aecc.STATE_PARAM_CONTACT_ID";

    private int contactId;
    private ContactModel actualContact;
    private ContactComponent contactComponent;
    @InjectView(R.id.nombreInsert)
    EditText nombreInsert;
    @InjectView(R.id.apellidosInsert)
    EditText apellidosInsert;
    @InjectView(R.id.direccionInsert)
    EditText direccionInsert;
    @InjectView(R.id.telefonoInsert)
    EditText telefonoInsert;
    @InjectView(R.id.emailInsert)
    EditText emailInsert;
    @InjectView(R.id.guardarPersonaje)
    Button saveContactButton;
    @Inject
    ContactDetailsPresenter contactDetailsPresenter;

    public static Intent getCallingIntent(Context context, int contactId) {
        Intent callingIntent = new Intent(context, PersonajesDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CONTACT_ID, contactId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        setContentView(R.layout.activity_personaje);
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
        Log.e("EEEOOO","El contactId es: " + this.contactId);
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
        this.contactDetailsPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.contactDetailsPresenter.pause();
    }

    @OnClick(R.id.guardarPersonaje)
    public void saveContact(){
        //TODO: esto no deberia seer model, deberia ser de view pero bueno
        ContactModel contactToSave = new ContactModel(actualContact.getContactId());
        contactToSave.setEmail(emailInsert.getText().toString());
        contactToSave.setName(nombreInsert.getText().toString());
        contactToSave.setPhone(telefonoInsert.getText().toString());
        contactToSave.setSurname(apellidosInsert.getText().toString());
        this.contactDetailsPresenter.saveContact(contactToSave);
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.contactDetailsPresenter.setView(this);
        this.contactDetailsPresenter.initialize(this.contactId);
    }

    @Override public void renderContact(ContactModel contact) {
        if (contact != null) {
            actualContact = contact;
            this.nombreInsert.setText(contact.getName());
            this.apellidosInsert.setText(contact.getSurname());
            this.direccionInsert.setText(contact.getCategory());
            this.telefonoInsert.setText(contact.getPhone());
            this.emailInsert.setText(contact.getEmail());
        }
    }

    @Override
    public void showOKMessage() {
        this.showToastMessage("El contacto se ha guardado correctamente");
    }
}
