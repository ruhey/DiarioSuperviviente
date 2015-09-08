package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.SymptomModule;
import org.aecc.superdiary.presentation.view.activity.SintomaCreateActivity;
import org.aecc.superdiary.presentation.view.activity.SintomaDeleteActivity;
import org.aecc.superdiary.presentation.view.activity.SintomaEditActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasDetailsActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasActivity;
import org.aecc.superdiary.presentation.view.activity.SintomasDetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SymptomModule.class})
public interface SymptomComponent extends ActivityComponent {

    void inject(SintomasActivity sintomasActivity);

    void inject(SintomasDetailsActivity sintomasDetailsActivity);

    void inject(SintomaEditActivity sintomaEditActivity);

    void inject(SintomaDeleteActivity sintomaDeleteActivity);

    void inject(SintomaCreateActivity sintomaCreateActivity);
}