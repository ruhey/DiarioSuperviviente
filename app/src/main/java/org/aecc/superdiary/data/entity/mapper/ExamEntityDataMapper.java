package org.aecc.superdiary.data.entity.mapper;

import org.aecc.superdiary.data.entity.ExamEntity;
import org.aecc.superdiary.domain.Exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExamEntityDataMapper {

    @Inject
    public ExamEntityDataMapper() {
    }

    public Exam transform(ExamEntity examEntity) {
        Exam exam = null;

        if (examEntity != null) {
            exam = new Exam(examEntity.getExamId());
            exam.setName(examEntity.getName());
            exam.setDateExam(examEntity.getDateExam());
            exam.setHourExam(examEntity.getHourExam());
            exam.setDescription(examEntity.getDescription());
            //TODO:ver como hacer lo de la imagen
            exam.setImage(examEntity.getImage());
        }
        return exam;
    }

    public ExamEntity untransform(Exam exam) {
        ExamEntity examEntity = null;

        if (exam != null) {
            examEntity.setExamId(exam.getExamId());
            examEntity.setName(exam.getName());
            examEntity.setHourExam(exam.getHourExam());
            examEntity.setDateExam(exam.getDateExam());
            examEntity.setDescription(exam.getDescription());
            //TODO:ver como hacer lo de la imagen
            examEntity.setImage(exam.getImage());
        }
        return examEntity;
    }

    public Collection<Exam> transform(Collection<ExamEntity> examEntityCollection) {
        List<Exam> examList = new ArrayList<Exam>();
        Exam exam;
        for (ExamEntity examEntity : examEntityCollection) {
            exam = transform(examEntity);
            if (exam != null) {
                examList.add(exam);
            }
        }
        return examList;
    }

    public Collection<ExamEntity> untransform(Collection<Exam> examCollection) {
        List<ExamEntity> examEntityList = new ArrayList<ExamEntity>();
        ExamEntity examEntity;
        for (Exam exam : examCollection) {
            examEntity = untransform(exam);
            if (examEntity != null) {
                examEntityList.add(examEntity);
            }
        }
        return examEntityList;
    }
}
