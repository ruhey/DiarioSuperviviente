package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.RoutineModule;
import org.aecc.superdiary.presentation.view.activity.RutinasActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, RoutineModule.class})
public interface RoutineComponent extends ActivityComponent {

    void inject(RutinasActivity rutinasActivity);
}