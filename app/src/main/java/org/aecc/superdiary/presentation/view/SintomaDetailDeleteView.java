package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.SymptomModel;

public interface SintomaDetailDeleteView extends LoadDataView{

    void deleteSymptom(int symptomId);

    void showMessage(String message);

    void renderSymptom(SymptomModel symptom);
}
