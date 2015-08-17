package org.aecc.superdiary.data.repository;

import org.aecc.superdiary.data.entity.ExamEntity;
import org.aecc.superdiary.data.entity.mapper.ExamEntityDataMapper;
import org.aecc.superdiary.data.exception.CantCreateExamException;
import org.aecc.superdiary.data.exception.CantSaveExamException;
import org.aecc.superdiary.data.exception.ExamNotFoundException;
import org.aecc.superdiary.data.exception.RepositoryErrorBundle;
import org.aecc.superdiary.data.repository.datasource.ExamDataStore;
import org.aecc.superdiary.data.repository.datasource.ExamDataStoreFactory;
import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.domain.repository.ExamRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExamDataRepository implements ExamRepository {

    private final ExamDataStoreFactory examDataStoreFactory;
    private final ExamEntityDataMapper examEntityDataMapper;

    @Inject
    public ExamDataRepository(ExamDataStoreFactory examDataStoreFactory, ExamEntityDataMapper examEntityDataMapper) {
        this.examDataStoreFactory = examDataStoreFactory;
        this.examEntityDataMapper = examEntityDataMapper;
    }

    @Override
    public void getExamList(final ExamListCallback examListCallback) {
        final ExamDataStore examDataStore = this.examDataStoreFactory.createDatabaseDataStore();
        examDataStore.getExamsEntityList(new ExamDataStore.ExamListCallback() {
            @Override
            public void onExamListLoaded(Collection<ExamEntity> examsCollection) {
                Collection<Exam> exams =
                        ExamDataRepository.this.examEntityDataMapper.transform(examsCollection);
                examListCallback.onExamListLoaded(exams);
            }

            @Override
            public void onError(Exception exception) {
                examListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void getExamById(final int examId, final ExamDetailsCallback examCallback) {
        ExamDataStore examDataStore = this.examDataStoreFactory.create(examId);
        examDataStore.getExamEntityDetails(examId, new ExamDataStore.ExamDetailsCallback() {
            @Override
            public void onExamEntityLoaded(ExamEntity examEntity) {
                Exam exam = ExamDataRepository.this.examEntityDataMapper.transform(examEntity);
                if (exam != null) {
                    examCallback.onExamLoaded(exam);
                } else {
                    examCallback.onError(new RepositoryErrorBundle(new ExamNotFoundException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                examCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void createExam(final Exam exam, final ExamCreationCallback examCreateCallback) {
        final ExamDataStore examDataStore = this.examDataStoreFactory.createDatabaseDataStore();
        examDataStore.createExamEntity(exam, new ExamDataStore.ExamCreationCallback() {

            @Override
            public void onExamCreated(ExamEntity examEntity) {
                Exam exam = ExamDataRepository.this.examEntityDataMapper.transform(examEntity);
                if (exam != null) {
                    examCreateCallback.onExamCreated(exam);
                } else {
                    examCreateCallback.onError(new RepositoryErrorBundle(new CantCreateExamException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                examCreateCallback.onError(new RepositoryErrorBundle(new CantCreateExamException()));
            }
        });
    }

    @Override
    public void saveExam(final Exam exam, final ExamSaveCallback examSaveCallback) {
        final ExamDataStore examDataStore = this.examDataStoreFactory.createDatabaseDataStore();
        examDataStore.saveExamEntity(exam, new ExamDataStore.ExamSaveCallback() {

            @Override
            public void onExamSaved(ExamEntity examEntity) {
                Exam exam = ExamDataRepository.this.examEntityDataMapper.transform(examEntity);
                if (exam != null) {
                    examSaveCallback.onExamSaved(exam);
                } else {
                    examSaveCallback.onError(new RepositoryErrorBundle(new CantSaveExamException()));
                }
            }

            @Override
            public void onError(Exception exception) {
                examSaveCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    @Override
    public void deleteExam(final int examId, final ExamDetionCallback examDeletionCallback) {
        final ExamDataStore examDataStore = this.examDataStoreFactory.createDatabaseDataStore();
        examDataStore.deleteExamEntity(examId, new ExamDataStore.ExamDeletionCallback() {
            @Override
            public void onExamDeleted(Collection<ExamEntity> examsCollection) {
                Collection<Exam> exams =
                        ExamDataRepository.this.examEntityDataMapper.transform(examsCollection);
                examDeletionCallback.onExamDeleted(exams);
            }

            @Override
            public void onError(Exception exception) {
                examDeletionCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
