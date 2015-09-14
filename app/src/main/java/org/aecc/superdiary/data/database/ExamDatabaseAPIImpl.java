package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.ExamEntity;

import java.util.ArrayList;
import java.util.Collection;

public class ExamDatabaseAPIImpl implements ExamDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] examListColumns = {DatabaseHelper.EXAMS_COLUMN_ID,
            DatabaseHelper.EXAMS_COLUMN_NAME};
    private String[] examAllColumns = {DatabaseHelper.EXAMS_COLUMN_ID,
            DatabaseHelper.EXAMS_COLUMN_NAME,
            DatabaseHelper.EXAMS_COLUMN_DATE,
            DatabaseHelper.EXAMS_COLUMN_HOUR,
            DatabaseHelper.EXAMS_COLUMN_DESCRIPTION,
            DatabaseHelper.EXAMS_COLUMN_IMAGE};

    public ExamDatabaseAPIImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.databaseHelper = new DatabaseHelper(this.context);
        //this.userEntityJsonMapper = userEntityJsonMapper;
    }

    private void openHelper() {
        database = databaseHelper.getWritableDatabase();
    }

    private void closeHelper() {
        databaseHelper.close();
    }

    @Override
    public void getExamEntityList(ExamListCallback examListCallback) {
        if (examListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<ExamEntity> examsCollection = new ArrayList<ExamEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXAMS,
                examListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ExamEntity exam = cursorToListItemExam(cursor);
            examsCollection.add(exam);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        examListCallback.onExamListLoaded(examsCollection);
    }

    @Override
    public void getExamEntityById(int examId, ExamDetailsCallback examDetailsCallback) {
        if (examDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXAMS,
                examAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ExamEntity examEntity = null;
        while (!cursor.isAfterLast()) {
            examEntity = cursorToDetailExam(cursor);
            if(examEntity.getExamId()==examId) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        examDetailsCallback.onExamEntityLoaded(examEntity);
        //ExamEntity examEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createExamEntity(ExamEntity exam, ExamCreationCallback examCreationCallback) {
        if (examCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EXAMS_COLUMN_NAME, exam.getName());
        values.put(DatabaseHelper.EXAMS_COLUMN_DATE, exam.getDateExam());
        values.put(DatabaseHelper.EXAMS_COLUMN_HOUR, exam.getHourExam());
        values.put(DatabaseHelper.EXAMS_COLUMN_DESCRIPTION, exam.getDescription());
        values.put(DatabaseHelper.EXAMS_COLUMN_IMAGE, exam.getImage());

        long insertId = database.insert(DatabaseHelper.TABLE_EXAMS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXAMS,
                examAllColumns, DatabaseHelper.EXAMS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ExamEntity newExamEntity = cursorToDetailExam(cursor);
        cursor.close();

        closeHelper();
        examCreationCallback.onExamEntityCreated(newExamEntity);
    }

    @Override
    public void saveExamEntity(final ExamEntity exam, final ExamSaveCallback examSaveCallback) {
        if (examSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EXAMS_COLUMN_ID, exam.getExamId());
        values.put(DatabaseHelper.EXAMS_COLUMN_NAME, exam.getName());
        values.put(DatabaseHelper.EXAMS_COLUMN_DATE, exam.getDateExam());
        values.put(DatabaseHelper.EXAMS_COLUMN_HOUR, exam.getHourExam());
        values.put(DatabaseHelper.EXAMS_COLUMN_DESCRIPTION, exam.getDescription());
        values.put(DatabaseHelper.EXAMS_COLUMN_IMAGE, exam.getImage());
        openHelper();

        database.update(DatabaseHelper.TABLE_EXAMS, values, DatabaseHelper.EXAMS_COLUMN_ID + " = ?", new String[]{String.valueOf(exam.getExamId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXAMS,
                examAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ExamEntity examEntity = null;
        while (!cursor.isAfterLast()) {
            examEntity = cursorToDetailExam(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        examSaveCallback.onExamEntitySaved(examEntity);
    }

    @Override
    public void deleteExamEntity(int examId, ExamDeletionCallback examDeletionCallback) {
        if (examDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_EXAMS, DatabaseHelper.EXAMS_COLUMN_ID
                + " = " + examId, null);
        Collection<ExamEntity> examsCollection = new ArrayList<ExamEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_EXAMS,
                examListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ExamEntity exam = cursorToListItemExam(cursor);
            examsCollection.add(exam);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        examDeletionCallback.onExamEntityDeleted(examsCollection);
    }

    private ExamEntity cursorToListItemExam(Cursor cursor) {
        ExamEntity exam = new ExamEntity();
        exam.setExamId(cursor.getInt(0));
        exam.setName(cursor.getString(1));
        return exam;
    }
    //private final ExamEntityJsonMapper examEntityJsonMapper;


    private ExamEntity cursorToDetailExam(Cursor cursor) {
        ExamEntity exam = new ExamEntity();
        exam.setExamId(cursor.getInt(0));
        exam.setName(cursor.getString(1));
        exam.setDateExam(cursor.getString(2));
        exam.setHourExam(cursor.getString(3));
        exam.setDescription(cursor.getString(4));
        exam.setImage(cursor.getString(5));
        return exam;
    }
}
