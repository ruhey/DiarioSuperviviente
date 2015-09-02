package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.SymptomModule;
import org.aecc.superdiary.presentation.view.activity.SintomasActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SymptomModule.class})
public interface SymptomComponent extends ActivityComponent {

    void inject(SintomasActivity sintomasActivity);
}