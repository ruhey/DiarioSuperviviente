package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ExamModel;

public interface PruebaDetailDeleteView extends LoadDataView{

    void deleteExam(int examId);

    void showMessage(String message);

    void renderExam(ExamModel exam);

    void goToList();
}
