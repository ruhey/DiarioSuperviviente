package org.aecc.superdiary.presentation.view;

import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

public interface PruebaDetailEditView extends LoadDataView {

    void renderExam(ExamModel exam);

    void editExam(int examId);

    void showMessage(String message);
}