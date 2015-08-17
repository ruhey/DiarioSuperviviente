package org.aecc.superdiary.domain.repository;

import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.exception.ErrorBundle;

import java.util.Collection;

public interface ExamRepository {

    void getExamList(ExamListCallback ExamListCallback);

    void getExamById(final int examId, ExamDetailsCallback examCallback);

    void createExam(final Exam exam, ExamCreationCallback examCallback);

    void saveExam(final Exam exam, ExamSaveCallback examCallback);

    void deleteExam(final int examId, ExamDetionCallback examDeletionCallback);

    interface ExamListCallback {
        void onExamListLoaded(Collection<Exam> examsCollection);

        void onError(ErrorBundle errorBundle);
    }

    interface ExamDetailsCallback {
        void onExamLoaded(Exam exam);

        void onError(ErrorBundle errorBundle);
    }

    interface ExamCreationCallback {
        void onExamCreated(Exam exam);

        void onError(ErrorBundle errorBundle);
    }

    interface ExamSaveCallback {
        void onExamSaved(Exam exam);

        void onError(ErrorBundle errorBundle);
    }

    interface ExamDetionCallback {
        void onExamDeleted(Collection<Exam> examsCollection);

        void onError(ErrorBundle errorBundle);
    }
}
