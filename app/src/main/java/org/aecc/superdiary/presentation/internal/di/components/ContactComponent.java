package org.aecc.superdiary.presentation.internal.di.components;


import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.internal.di.modules.ActivityModule;
import org.aecc.superdiary.presentation.internal.di.modules.ContactModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ContactModule.class})
public interface ContactComponent extends ActivityComponent {
   // void inject(UserListFragment userListFragment);
    //void inject(UserDetailsFragment userDetailsFragment);
}