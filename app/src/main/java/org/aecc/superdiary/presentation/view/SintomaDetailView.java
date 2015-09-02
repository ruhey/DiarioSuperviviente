package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.SymptomModel;

public interface SintomaDetailView extends LoadDataView {

    void renderSymptom(SymptomModel symptom);
}
