package org.aecc.superdiary.presentation.internal.di.components;

import android.content.Context;

import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;
import org.aecc.superdiary.presentation.internal.di.modules.ApplicationModule;
import org.aecc.superdiary.presentation.view.activity.BaseActivity;
import org.aecc.superdiary.presentation.view.activity.DiaryBaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    ContactRepository contactRepository();
}
