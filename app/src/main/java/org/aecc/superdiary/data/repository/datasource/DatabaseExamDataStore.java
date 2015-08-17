package org.aecc.superdiary.data.repository.datasource;

import org.aecc.superdiary.data.cache.ExamCache;
import org.aecc.superdiary.data.database.ExamDatabaseAPI;
import org.aecc.superdiary.data.entity.ExamEntity;
import org.aecc.superdiary.data.entity.mapper.ExamEntityDataMapper;
import org.aecc.superdiary.domain.Exam;

import java.util.Collection;

public class DatabaseExamDataStore implements ExamDataStore {
    private final ExamCache examCache;
    private final ExamDatabaseAPI databaseAPI;
    private final ExamEntityDataMapper examEntityDataMapper;

    public DatabaseExamDataStore(ExamCache examCache, ExamDatabaseAPI databaseAPI, ExamEntityDataMapper examEntityDataMapper) {
        this.examCache = examCache;
        this.databaseAPI = databaseAPI;
        this.examEntityDataMapper = examEntityDataMapper;

    }

    @Override
    public void getExamsEntityList(final ExamListCallback examListCallback) {
        this.databaseAPI.getExamEntityList(new ExamDatabaseAPI.ExamListCallback() {

            @Override
            public void onExamListLoaded(Collection<ExamEntity> examsCollection) {
                examListCallback.onExamListLoaded(examsCollection);
            }

            @Override
            public void onError(Exception exception) {
                examListCallback.onError(exception);
            }
        });
    }

    @Override
    public void getExamEntityDetails(int id, final ExamDetailsCallback examDetailsCallback) {
        this.databaseAPI.getExamEntityById(id, new ExamDatabaseAPI.ExamDetailsCallback() {

            @Override
            public void onExamEntityLoaded(ExamEntity examEntity) {
                examDetailsCallback.onExamEntityLoaded(examEntity);
                //CloudUserDataStore.this.putExamEntityInCache(examEntity);
            }

            @Override
            public void onError(Exception exception) {
                examDetailsCallback.onError(exception);
            }
        });
    }

    @Override
    public void createExamEntity(final Exam exam, final ExamCreationCallback examCreationCallback) {
        this.databaseAPI.createExamEntity(this.examEntityDataMapper.untransform(exam), new ExamDatabaseAPI.ExamCreationCallback() {

            @Override
            public void onExamEntityCreated(ExamEntity examEntity) {
                examCreationCallback.onExamCreated(examEntity);
            }

            @Override
            public void onError(Exception exception) {
                examCreationCallback.onError(exception);
            }
        });
    }

    @Override
    public void saveExamEntity(final Exam exam, final ExamSaveCallback examSaveCallback) {
        this.databaseAPI.saveExamEntity(this.examEntityDataMapper.untransform(exam), new ExamDatabaseAPI.ExamSaveCallback() {
            @Override
            public void onExamEntitySaved(ExamEntity examEntity) {
                examSaveCallback.onExamSaved(examEntity);
            }

            @Override
            public void onError(Exception exception) {
                examSaveCallback.onError(exception);
            }
        });

    }

    @Override
    public void deleteExamEntity(final int id, final ExamDeletionCallback examDeletionCallback) {
        this.databaseAPI.deleteExamEntity(id, new ExamDatabaseAPI.ExamDeletionCallback() {
            @Override
            public void onExamEntityDeleted(Collection<ExamEntity> examsCollection) {
                examDeletionCallback.onExamDeleted(examsCollection);
            }

            @Override
            public void onError(Exception exception) {
                examDeletionCallback.onError(exception);
            }
        });
    }


}
