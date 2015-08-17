package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.RoutineEntity;

import java.util.ArrayList;
import java.util.Collection;

public class RoutineDatabaseAPIImpl implements RoutineDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] routineListColumns = {DatabaseHelper.ROUTINES_COLUMN_ID,
            DatabaseHelper.ROUTINES_COLUMN_NAME};

    private String[] routineAllColumns = {DatabaseHelper.ROUTINES_COLUMN_ID,
            DatabaseHelper.ROUTINES_COLUMN_NAME,
            DatabaseHelper.ROUTINES_COLUMN_PLACE,
            DatabaseHelper.ROUTINES_COLUMN_DESCRIPTION,
            DatabaseHelper.ROUTINES_COLUMN_DATEROUTINE,
            DatabaseHelper.ROUTINES_COLUMN_HOURROUTINE,
            DatabaseHelper.ROUTINES_COLUMN_DATEALARM,
            DatabaseHelper.ROUTINES_COLUMN_HOURALARM,
            DatabaseHelper.ROUTINES_COLUMN_DURATION,
            DatabaseHelper.ROUTINES_COLUMN_SATISFACTION,
            DatabaseHelper.ROUTINES_COLUMN_IMAGE};

    public RoutineDatabaseAPIImpl(Context context) {
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
    public void getRoutineEntityList(RoutineListCallback routineListCallback) {
        if (routineListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<RoutineEntity> routinesCollection = new ArrayList<RoutineEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUTINES,
                routineListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RoutineEntity routine = cursorToListItemRoutine(cursor);
            routinesCollection.add(routine);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        routineListCallback.onRoutineListLoaded(routinesCollection);
    }

    @Override
    public void getRoutineEntityById(int routineId, RoutineDetailsCallback routineDetailsCallback) {
        if (routineDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUTINES,
                routineAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        RoutineEntity routineEntity = null;
        while (!cursor.isAfterLast()) {
            routineEntity = cursorToDetailRoutine(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        routineDetailsCallback.onRoutineEntityLoaded(routineEntity);
        //RoutineEntity routineEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createRoutineEntity(RoutineEntity routine, RoutineCreationCallback routineCreationCallback) {
        if (routineCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ROUTINES_COLUMN_NAME, routine.getName());
        values.put(DatabaseHelper.ROUTINES_COLUMN_PLACE, routine.getPlace());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DESCRIPTION, routine.getDescription());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DATEROUTINE, routine.getDateRoutine());
        values.put(DatabaseHelper.ROUTINES_COLUMN_HOURROUTINE, routine.getHourRoutine());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DATEALARM, routine.getDateAlarm());
        values.put(DatabaseHelper.ROUTINES_COLUMN_HOURALARM, routine.getHourAlarm());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DURATION, routine.getDuration());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DURATION, routine.getSatisfaction());
        values.put(DatabaseHelper.ROUTINES_COLUMN_IMAGE, routine.getImage());

        long insertId = database.insert(DatabaseHelper.TABLE_ROUTINES, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUTINES,
                routineAllColumns, DatabaseHelper.ROUTINES_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        RoutineEntity newRoutineEntity = cursorToDetailRoutine(cursor);
        cursor.close();

        closeHelper();
        routineCreationCallback.onRoutineEntityCreated(newRoutineEntity);
    }

    @Override
    public void saveRoutineEntity(final RoutineEntity routine, final RoutineSaveCallback routineSaveCallback) {
        if (routineSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ROUTINES_COLUMN_ID, routine.getRoutineId());
        values.put(DatabaseHelper.ROUTINES_COLUMN_NAME, routine.getName());
        values.put(DatabaseHelper.ROUTINES_COLUMN_PLACE, routine.getPlace());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DESCRIPTION, routine.getDescription());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DATEROUTINE, routine.getDateRoutine());
        values.put(DatabaseHelper.ROUTINES_COLUMN_HOURROUTINE, routine.getHourRoutine());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DATEALARM, routine.getDateAlarm());
        values.put(DatabaseHelper.ROUTINES_COLUMN_HOURALARM, routine.getHourAlarm());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DURATION, routine.getDuration());
        values.put(DatabaseHelper.ROUTINES_COLUMN_DURATION, routine.getSatisfaction());
        values.put(DatabaseHelper.ROUTINES_COLUMN_IMAGE, routine.getImage());
        openHelper();

        database.update(DatabaseHelper.TABLE_ROUTINES, values, DatabaseHelper.ROUTINES_COLUMN_ID + " = ?", new String[]{String.valueOf(routine.getRoutineId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUTINES,
                routineAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        RoutineEntity routineEntity = null;
        while (!cursor.isAfterLast()) {
            routineEntity = cursorToDetailRoutine(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        routineSaveCallback.onRoutineEntitySaved(routineEntity);
    }

    @Override
    public void deleteRoutineEntity(int routineId, RoutineDeletionCallback routineDeletionCallback) {
        if (routineDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_ROUTINES, DatabaseHelper.ROUTINES_COLUMN_ID
                + " = " + routineId, null);
        Collection<RoutineEntity> routinesCollection = new ArrayList<RoutineEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUTINES,
                routineListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RoutineEntity routine = cursorToListItemRoutine(cursor);
            routinesCollection.add(routine);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        routineDeletionCallback.onRoutineEntityDeleted(routinesCollection);
    }

    private RoutineEntity cursorToListItemRoutine(Cursor cursor) {
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(cursor.getInt(0));
        routine.setName(cursor.getString(1));
        return routine;
    }
    //private final RoutineEntityJsonMapper routineEntityJsonMapper;

    private RoutineEntity cursorToDetailRoutine(Cursor cursor) {
        RoutineEntity routine = new RoutineEntity();
        routine.setRoutineId(cursor.getInt(0));
        routine.setName(cursor.getString(1));
        routine.setPlace(cursor.getString(2));
        routine.setDescription(cursor.getString(3));
        routine.setDateRoutine(cursor.getString(4));
        routine.setHourRoutine(cursor.getString(5));
        routine.setDateAlarm(cursor.getString(6));
        routine.setHourAlarm(cursor.getString(7));
        routine.setDuration(cursor.getString(8));
        routine.setSatisfaction(cursor.getString(9));
        routine.setImage(cursor.getString(10));
        return routine;
    }
}
