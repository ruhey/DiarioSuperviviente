package org.aecc.superdiary.presentation.view.adapter;

import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

import java.util.Locale;

public interface PruebaDetailEditView extends LoadDataView {

    void renderExam(ExamModel exam);

    void editExam(int examId);

    void showMessage(String message);
}
