package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.RoutineComponent;
import org.aecc.superdiary.presentation.view.RutinaDetailCreateView;

public class RutinaCreateActivity extends BaseActivity implements RutinaDetailCreateView, HasComponent<RoutineComponent> {
    @Override
    public RoutineComponent getComponent() {
        return null;
    }

    @Override
    public void createRoutine(int routineId) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
