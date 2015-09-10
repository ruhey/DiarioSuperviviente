package org.aecc.superdiary.presentation.view;


import org.aecc.superdiary.presentation.model.SymptomModel;

import java.util.Collection;

public interface SintomasListView extends LoadDataView {

    void renderSymptomList(Collection<SymptomModel> symptomModelCollection);

    void viewSymptom(SymptomModel symptomModel);

    void addSymptom();
}
