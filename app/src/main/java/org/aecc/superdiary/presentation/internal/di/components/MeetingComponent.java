package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.MeetingModule;
import org.aecc.superdiary.presentation.view.activity.CitasActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MeetingModule.class})
public interface MeetingComponent extends ActivityComponent {

    void inject(CitasActivity citasActivity);
}