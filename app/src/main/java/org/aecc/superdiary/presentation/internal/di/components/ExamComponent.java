package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.ExamModule;
import org.aecc.superdiary.presentation.view.activity.PruebaCreateActivity;
import org.aecc.superdiary.presentation.view.activity.PruebaDeleteActivity;
import org.aecc.superdiary.presentation.view.activity.PruebaEditActivity;
import org.aecc.superdiary.presentation.view.activity.PruebasActivity;
import org.aecc.superdiary.presentation.view.activity.PruebasDetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ExamModule.class})
public interface ExamComponent extends ActivityComponent {

    void inject(PruebasActivity pruebasActivity);

    void inject(PruebasDetailsActivity pruebasDetailsActivity);

    void inject(PruebaEditActivity pruebaEditActivity);

    void inject(PruebaDeleteActivity pruebaDeleteActivity);

    void inject(PruebaCreateActivity pruebaCreateActivity);
}