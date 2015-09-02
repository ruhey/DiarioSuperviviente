package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.ExamModule;
import org.aecc.superdiary.presentation.view.activity.PruebasActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ExamModule.class})
public interface ExamComponent extends ActivityComponent {

    void inject(PruebasActivity pruebasActivity);
}