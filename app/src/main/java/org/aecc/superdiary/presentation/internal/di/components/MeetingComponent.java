package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.MeetingModule;
import org.aecc.superdiary.presentation.view.activity.CitaNoEditActivity;
import org.aecc.superdiary.presentation.view.activity.CitasActivity;
import org.aecc.superdiary.presentation.view.activity.CitasDetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MeetingModule.class})
public interface MeetingComponent extends ActivityComponent {

    void inject(CitasActivity citasActivity);

    void inject(CitasDetailsActivity citasDetailsActivity);

    void inject(CitaNoEditActivity citaNoEditActivity);
}