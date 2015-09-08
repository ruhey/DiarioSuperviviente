package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.MedicineComponent;
import org.aecc.superdiary.presentation.view.MedicamentoDetailCreateView;

public class MedicamentoCreateActivity extends BaseActivity implements MedicamentoDetailCreateView, HasComponent<MedicineComponent> {
    @Override
    public MedicineComponent getComponent() {
        return null;
    }

    @Override
    public void createMedicine(int medicineId) {

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
