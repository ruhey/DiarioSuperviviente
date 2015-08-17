package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.SymptomEntity;

import java.util.ArrayList;
import java.util.Collection;

public class SymptomDatabaseAPIImpl implements SymptomDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] symptomListColumns = {DatabaseHelper.SYMPTOMS_COLUMN_ID,
            DatabaseHelper.SYMPTOMS_COLUMN_NAME};
    private String[] symptomAllColumns = {DatabaseHelper.SYMPTOMS_COLUMN_ID,
            DatabaseHelper.SYMPTOMS_COLUMN_NAME,
            DatabaseHelper.SYMPTOMS_COLUMN_DATE,
            DatabaseHelper.SYMPTOMS_COLUMN_HOUR,
            DatabaseHelper.SYMPTOMS_COLUMN_DESCRIPTION,
            DatabaseHelper.SYMPTOMS_COLUMN_IMAGE};

    public SymptomDatabaseAPIImpl(Context context) {
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
    public void getSymptomEntityList(SymptomListCallback symptomListCallback) {
        if (symptomListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<SymptomEntity> symptomsCollection = new ArrayList<SymptomEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYMPTOMS,
                symptomListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SymptomEntity symptom = cursorToListItemSymptom(cursor);
            symptomsCollection.add(symptom);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        symptomListCallback.onSymptomListLoaded(symptomsCollection);
    }

    @Override
    public void getSymptomEntityById(int symptomId, SymptomDetailsCallback symptomDetailsCallback) {
        if (symptomDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYMPTOMS,
                symptomAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        SymptomEntity symptomEntity = null;
        while (!cursor.isAfterLast()) {
            symptomEntity = cursorToDetailSymptom(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        symptomDetailsCallback.onSymptomEntityLoaded(symptomEntity);
        //SymptomEntity symptomEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createSymptomEntity(SymptomEntity symptom, SymptomCreationCallback symptomCreationCallback) {
        if (symptomCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_NAME, symptom.getName());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_DATE, symptom.getDateSymptom());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_HOUR, symptom.getHourSymptom());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_DESCRIPTION, symptom.getDescription());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_IMAGE, symptom.getImage());

        long insertId = database.insert(DatabaseHelper.TABLE_SYMPTOMS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYMPTOMS,
                symptomAllColumns, DatabaseHelper.SYMPTOMS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        SymptomEntity newSymptomEntity = cursorToDetailSymptom(cursor);
        cursor.close();

        closeHelper();
        symptomCreationCallback.onSymptomEntityCreated(newSymptomEntity);
    }

    @Override
    public void saveSymptomEntity(final SymptomEntity symptom, final SymptomSaveCallback symptomSaveCallback) {
        if (symptomSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_ID, symptom.getSymptomId());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_NAME, symptom.getName());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_DATE, symptom.getDateSymptom());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_HOUR, symptom.getHourSymptom());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_DESCRIPTION, symptom.getDescription());
        values.put(DatabaseHelper.SYMPTOMS_COLUMN_IMAGE, symptom.getImage());
        openHelper();

        database.update(DatabaseHelper.TABLE_SYMPTOMS, values, DatabaseHelper.SYMPTOMS_COLUMN_ID + " = ?", new String[]{String.valueOf(symptom.getSymptomId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYMPTOMS,
                symptomAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        SymptomEntity symptomEntity = null;
        while (!cursor.isAfterLast()) {
            symptomEntity = cursorToDetailSymptom(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        symptomSaveCallback.onSymptomEntitySaved(symptomEntity);
    }

    @Override
    public void deleteSymptomEntity(int symptomId, SymptomDeletionCallback symptomDeletionCallback) {
        if (symptomDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_SYMPTOMS, DatabaseHelper.SYMPTOMS_COLUMN_ID
                + " = " + symptomId, null);
        Collection<SymptomEntity> symptomsCollection = new ArrayList<SymptomEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_SYMPTOMS,
                symptomListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SymptomEntity symptom = cursorToListItemSymptom(cursor);
            symptomsCollection.add(symptom);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        symptomDeletionCallback.onSymptomEntityDeleted(symptomsCollection);
    }

    private SymptomEntity cursorToListItemSymptom(Cursor cursor) {
        SymptomEntity symptom = new SymptomEntity();
        symptom.setSymptomId(cursor.getInt(0));
        symptom.setName(cursor.getString(1));
        return symptom;
    }
    //private final SymptomEntityJsonMapper symptomEntityJsonMapper;


    private SymptomEntity cursorToDetailSymptom(Cursor cursor) {
        SymptomEntity symptom = new SymptomEntity();
        symptom.setSymptomId(cursor.getInt(0));
        symptom.setName(cursor.getString(1));
        symptom.setHourSymptom(cursor.getString(2));
        symptom.setDateSymptom(cursor.getString(3));
        symptom.setDescription(cursor.getString(4));
        symptom.setImage(cursor.getString(5));
        return symptom;
    }
}
