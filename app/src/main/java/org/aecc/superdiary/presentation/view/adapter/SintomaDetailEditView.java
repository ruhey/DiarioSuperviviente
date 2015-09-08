package org.aecc.superdiary.presentation.view.adapter;

import org.aecc.superdiary.presentation.model.SymptomModel;
import org.aecc.superdiary.presentation.view.LoadDataView;

import java.util.Locale;

public interface SintomaDetailEditView extends LoadDataView {

    void renderSymptom(SymptomModel symptom);

    void editSymptom(int symptomId);

    void showMessage(String message);
}
