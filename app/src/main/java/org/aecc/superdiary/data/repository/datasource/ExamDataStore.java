package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.entity.ExamEntity;
import org.aecc.superdiary.domain.Exam;

import java.util.Collection;

public interface ExamDataStore {

    void getExamsEntityList(ExamListCallback examListCallback);

    void getExamEntityDetails(int id, ExamDetailsCallback examDetailsCallback);

    void createExamEntity(Exam exam, ExamCreationCallback examCallback);

    void saveExamEntity(Exam exam, ExamSaveCallback examCallback);

    void deleteExamEntity(int id, ExamDeletionCallback examDeletionCallback);

    interface ExamListCallback {
        void onExamListLoaded(Collection<ExamEntity> examsCollection);

        void onError(Exception exception);
    }

    interface ExamDetailsCallback {
        void onExamEntityLoaded(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamCreationCallback {
        void onExamCreated(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamSaveCallback {
        void onExamSaved(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamDeletionCallback {
        void onExamDeleted(Collection<ExamEntity> examsCollection);

        void onError(Exception exception);
    }
}
