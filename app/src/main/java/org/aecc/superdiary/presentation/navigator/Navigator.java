package org.aecc.superdiary.presentation.navigator;

import android.content.Context;
import android.content.Intent;

import org.aecc.superdiary.presentation.view.activity.PersonajesActivity;
import org.aecc.superdiary.presentation.view.activity.PersonajesDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public void Navigator() {
        //empty
    }

    public void navigateToContactList(Context context) {
        if (context != null) {
            Intent intentToLaunch = PersonajesActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToContacDetails(Context context, int contactId) {
        if (context != null) {
            Intent intentToLaunch = PersonajesDetailsActivity.getCallingIntent(context, contactId);
            context.startActivity(intentToLaunch);
        }
    }
}


