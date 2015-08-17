package org.aecc.superdiary.data.database;

import org.aecc.superdiary.data.entity.ExamEntity;

import java.util.Collection;

public interface ExamDatabaseAPI {

    void getExamEntityList(final ExamListCallback examListCallback);

    void getExamEntityById(final int examId, final ExamDetailsCallback examDetailsCallback);

    void createExamEntity(final ExamEntity exam, final ExamCreationCallback examCreationCallback);

    void saveExamEntity(final ExamEntity exam, final ExamSaveCallback examSaveCallback);

    void deleteExamEntity(final int examId, final ExamDeletionCallback examDeletionCallback);

    interface ExamListCallback {
        void onExamListLoaded(Collection<ExamEntity> examsCollection);

        void onError(Exception exception);
    }

    interface ExamDetailsCallback {
        void onExamEntityLoaded(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamCreationCallback {
        void onExamEntityCreated(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamSaveCallback {
        void onExamEntitySaved(ExamEntity examEntity);

        void onError(Exception exception);
    }

    interface ExamDeletionCallback {
        void onExamEntityDeleted(Collection<ExamEntity> examsCollection);

        void onError(Exception exception);
    }
}
