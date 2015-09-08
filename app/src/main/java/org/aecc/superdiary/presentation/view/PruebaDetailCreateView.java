package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ExamModel;

public interface PruebaDetailCreateView extends LoadDataView {



    void createExam(int examId);

    void showMessage(String message);
}
