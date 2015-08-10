package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerContactComponent;

public class PersonajesDetailsActivity extends BaseActivity implements HasComponent<ContactComponent> {

    private static final String INTENT_EXTRA_PARAM_CONTACT_ID = "org.aecc.INTENT_PARAM_CONTACT_ID";
    private static final String INSTANCE_STATE_PARAM_CONTACT_ID = "org.aecc.STATE_PARAM_CONTACT_ID";

    private int contactId;
    private ContactComponent contactComponent;

    public static Intent getCallingIntent(Context context, int contactId) {
        Intent callingIntent = new Intent(context, PersonajesDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_CONTACT_ID, contactId);

        return callingIntent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
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
    }

    private void initializeInjector() {
        this.contactComponent = DaggerContactComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public ContactComponent getComponent() {
        return contactComponent;
    }

}
