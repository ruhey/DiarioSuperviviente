package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.presenter.ContactDeletePresenter;
import org.aecc.superdiary.presentation.view.PersonajeDetailView;
import org.aecc.superdiary.presentation.view.PersonajesDeleteView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PersonajesDetailsDeleteActivity extends BaseActivity implements HasComponent<ContactComponent>, PersonajesDeleteView {

    private static final String INTENT_EXTRA_PARAM_CONTACT_ID = "org.aecc.INTENT_PARAM_CONTACT_ID";
    private static final String INSTANCE_STATE_PARAM_CONTACT_ID = "org.aecc.STATE_PARAM_CONTACT_ID";

    private int contactId;
    private ContactModel actualContact;
    private ContactComponent contactComponent;
    @Inject
    private ContactDeletePresenter contactDeletePresenter;


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


    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.contactDeletePresenter.setView(this);
        this.contactDeletePresenter.initialize(this.contactId);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.contactId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_CONTACT_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));

        } else {
            this.contactId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_CONTACT_ID);
        }
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
    public void renderContact(ContactModel contact) {
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
        this.showToastMessage("El contacto se ha borrado correctamente");
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
}
