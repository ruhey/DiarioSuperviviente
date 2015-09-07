package org.aecc.superdiary.presentation.navigator;

import android.content.Context;
import android.content.Intent;

import org.aecc.superdiary.presentation.view.activity.PersonajeDetailsNoEditActivity;
import org.aecc.superdiary.presentation.view.activity.PersonajesActivity;
import org.aecc.superdiary.presentation.view.activity.PersonajesDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.CitasActivity;
import org.aecc.superdiary.presentation.view.activity.CitasDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.PersonajesDetailsDeleteActivity;
import org.aecc.superdiary.presentation.view.activity.RutinasActivity;
import org.aecc.superdiary.presentation.view.activity.RutinasDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.PruebasActivity;
import org.aecc.superdiary.presentation.view.activity.PruebasDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;


public class Navigator {

    @Inject
    @Singleton
    public Navigator() {
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
            Intent intentToLaunch = PersonajeDetailsNoEditActivity.getCallingIntent(context, contactId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToContacDetailsEdit(Context context, int contactId) {
        if (context != null) {
            Intent intentToLaunch = PersonajesDetailsActivity.getCallingIntent(context, contactId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToContacDetailsDelete(Context context, int contactId) {
        if (context != null) {
            Intent intentToLaunch = PersonajesDetailsDeleteActivity.getCallingIntent(context, contactId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMeetingList(Context context) {
        if (context != null) {
            Intent intentToLaunch = CitasActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMeetingDetails(Context context, int meetingId) {
        if (context != null) {
            Intent intentToLaunch = CitasDetailsActivity.getCallingIntent(context, meetingId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMedicineList(Context context) {
        if (context != null) {
            Intent intentToLaunch = MedicamentosActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMedicineDetails(Context context, int medicineId) {
        if (context != null) {
            Intent intentToLaunch = MedicamentosDetailsActivity.getCallingIntent(context, medicineId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToRoutineList(Context context) {
        if (context != null) {
            Intent intentToLaunch = RutinasActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToRoutineDetails(Context context, int routineId) {
        if (context != null) {
            Intent intentToLaunch = RutinasDetailsActivity.getCallingIntent(context, routineId);
            context.startActivity(intentToLaunch);
        }
    }
    public void navigateToExamList(Context context) {
        if (context != null) {
            Intent intentToLaunch = PruebasActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToExamDetails(Context context, int routineId) {
        if (context != null) {
            Intent intentToLaunch = PruebasDetailsActivity.getCallingIntent(context, routineId);
            context.startActivity(intentToLaunch);
        }
    }
    public void navigateToSymptomList(Context context) {
        if (context != null) {
            Intent intentToLaunch = SintomasActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSymptomDetails(Context context, int symptomId) {
        if (context != null) {
            Intent intentToLaunch = SintomasDetailsActivity.getCallingIntent(context, symptomId);
            context.startActivity(intentToLaunch);
        }
    }
}


