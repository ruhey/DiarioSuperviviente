package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.SymptomComponent;
import org.aecc.superdiary.presentation.view.SintomaDetailCreateView;

public class SintomaCreateActivity extends BaseActivity implements SintomaDetailCreateView, HasComponent<SymptomComponent> {
    @Override
    public SymptomComponent getComponent() {
        return null;
    }

    @Override
    public void createSymptom(int symptomId) {

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
