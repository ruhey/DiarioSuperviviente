package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.ContactModule;
import org.aecc.superdiary.presentation.view.activity.PersonajesActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ContactModule.class})
public interface ContactComponent extends ActivityComponent {

    void inject(PersonajesActivity personajesActivity);
}