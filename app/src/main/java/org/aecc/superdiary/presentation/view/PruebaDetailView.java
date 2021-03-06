package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ExamModel;

public interface PruebaDetailView extends LoadDataView {

    void renderExam(ExamModel exam);

    void editExam(int examId);

    void deleteExam(int examId);
}
