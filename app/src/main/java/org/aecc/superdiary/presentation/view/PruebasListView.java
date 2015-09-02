package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.ExamModel;

import java.util.Collection;

public interface PruebasListView extends LoadDataView {

    void renderExamList(Collection<ExamModel> examModelCollection);

    void viewExam(ExamModel examModel);
}
