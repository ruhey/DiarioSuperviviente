package org.aecc.superdiary.data.repository.datasource;

import android.content.Context;

import org.aecc.superdiary.data.cache.ExamCache;
import org.aecc.superdiary.data.database.ExamDatabaseAPI;
import org.aecc.superdiary.data.database.ExamDatabaseAPIImpl;
import org.aecc.superdiary.data.entity.mapper.ExamEntityDataMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExamDataStoreFactory {

    private final Context context;
    private final ExamCache examCache;
    private final ExamEntityDataMapper examEntityDataMapper;

    @Inject
    public ExamDataStoreFactory(Context context, ExamCache examCache, ExamEntityDataMapper examEntityDataMapper) {
        this.context = context;
        this.examCache = examCache;
        this.examEntityDataMapper = examEntityDataMapper;
    }

    public ExamDataStore create(int examId) {
        ExamDataStore examDataStore;

        if (!this.examCache.isExpired() && this.examCache.isCached(examId)) {
            //TODO: vamos a suar Cache?
            //examDataStore = new DiskExamDataStore(this.examCache);
            examDataStore = createDatabaseDataStore();
        } else {
            examDataStore = createDatabaseDataStore();
        }

        return examDataStore;
    }

    public ExamDataStore createDatabaseDataStore() {
        //TODO: implementar mejjor esta creacion
        ExamDatabaseAPI dataBaseAPI = new ExamDatabaseAPIImpl(this.context);
        return new DatabaseExamDataStore(this.examCache, dataBaseAPI, examEntityDataMapper);
    }
}
