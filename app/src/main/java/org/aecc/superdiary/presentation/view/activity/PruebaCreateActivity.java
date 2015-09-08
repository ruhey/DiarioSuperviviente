package org.aecc.superdiary.presentation.view.activity;


import android.content.Context;

import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.view.PruebaDetailCreateView;

public class PruebaCreateActivity extends BaseActivity implements PruebaDetailCreateView, HasComponent<ExamComponent> {
    @Override
    public ExamComponent getComponent() {
        return null;
    }

    @Override
    public void createExam(int examId) {

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
