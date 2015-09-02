package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.MedicineModule;
import org.aecc.superdiary.presentation.view.activity.MedicamentosActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MedicineModule.class})
public interface MedicineComponent extends ActivityComponent {

    void inject(MedicamentosActivity medicamentosActivity);
}