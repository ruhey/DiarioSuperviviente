package org.aecc.superdiary.presentation.mapper;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.model.ExamModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

@PerActivity
public class ExamModelDataMapper {

    @Inject
    public ExamModelDataMapper() {
    }

    public ExamModel transform(Exam exam) {
        if (exam == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ExamModel examModel = new ExamModel(exam.getExamId());
        examModel.setName(exam.getName());
        examModel.setDateExam(exam.getDateExam());
        examModel.setHourExam(exam.getHourExam());
        examModel.setDescription(exam.getDescription());
        examModel.setImage(exam.getImage());

        return examModel;
    }

    public Collection<ExamModel> transform(Collection<Exam> examsCollection) {
        Collection<ExamModel> examModelsCollection;

        if (examsCollection != null && !examsCollection.isEmpty()) {
            examModelsCollection = new ArrayList<ExamModel>();
            for (Exam exam : examsCollection) {
                examModelsCollection.add(transform(exam));
            }
        } else {
            examModelsCollection = Collections.emptyList();
        }

        return examModelsCollection;
    }
}
