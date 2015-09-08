package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.MedicineModule;
import org.aecc.superdiary.presentation.view.activity.MedicamentoCreateActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentoDeleteActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentoEditActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosActivity;
import org.aecc.superdiary.presentation.view.activity.MedicamentosDetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MedicineModule.class})
public interface MedicineComponent extends ActivityComponent {

    void inject(MedicamentosActivity medicamentosActivity);

    void inject(MedicamentosDetailsActivity medicamentosActivity);

    void inject(MedicamentoEditActivity medicamentoEditActivity);

    void inject(MedicamentoDeleteActivity medicamentoDeleteActivity);

    void inject(MedicamentoCreateActivity medicamentoCreateActivity);
    
    
}