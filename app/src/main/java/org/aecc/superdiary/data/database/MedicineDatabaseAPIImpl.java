package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.MedicineEntity;

import java.util.ArrayList;
import java.util.Collection;

public class MedicineDatabaseAPIImpl implements MedicineDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] medicineListColumns = {DatabaseHelper.MEDICINES_COLUMN_ID,
            DatabaseHelper.MEDICINES_COLUMN_NAME};

    private String[] medicineAllColumns = {DatabaseHelper.MEDICINES_COLUMN_ID,
            DatabaseHelper.MEDICINES_COLUMN_NAME,
            DatabaseHelper.MEDICINES_COLUMN_FIRSTDAY,
            DatabaseHelper.MEDICINES_COLUMN_FIRSTHOUR,
            DatabaseHelper.MEDICINES_COLUMN_LASTDAY,
            DatabaseHelper.MEDICINES_COLUMN_LASTHOUR,
            DatabaseHelper.MEDICINES_COLUMN_INTERVAL,
            DatabaseHelper.MEDICINES_COLUMN_DESCRIPTION,
            DatabaseHelper.MEDICINES_COLUMN_IMAGE};

    public MedicineDatabaseAPIImpl(Context context) {
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
    public void getMedicineEntityList(MedicineListCallback medicineListCallback) {
        if (medicineListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<MedicineEntity> medicinesCollection = new ArrayList<MedicineEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICINES,
                medicineListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MedicineEntity medicine = cursorToListItemMedicine(cursor);
            medicinesCollection.add(medicine);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        medicineListCallback.onMedicineListLoaded(medicinesCollection);
    }

    @Override
    public void getMedicineEntityById(int medicineId, MedicineDetailsCallback medicineDetailsCallback) {
        if (medicineDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICINES,
                medicineAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        MedicineEntity medicineEntity = null;
        while (!cursor.isAfterLast()) {
            medicineEntity = cursorToDetailMedicine(cursor);
            if(medicineEntity.getMedicineId()==medicineId) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        medicineDetailsCallback.onMedicineEntityLoaded(medicineEntity);
        //MedicineEntity medicineEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createMedicineEntity(MedicineEntity medicine, MedicineCreationCallback medicineCreationCallback) {
        if (medicineCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDICINES_COLUMN_NAME, medicine.getName());
        values.put(DatabaseHelper.MEDICINES_COLUMN_FIRSTDAY, medicine.getFirstDay());
        values.put(DatabaseHelper.MEDICINES_COLUMN_FIRSTHOUR, medicine.getFirstHour());
        values.put(DatabaseHelper.MEDICINES_COLUMN_LASTDAY,medicine.getLastDay());
        values.put(DatabaseHelper.MEDICINES_COLUMN_LASTHOUR,medicine.getLastHour());
        values.put(DatabaseHelper.MEDICINES_COLUMN_INTERVAL,medicine.getInterval());
        values.put(DatabaseHelper.MEDICINES_COLUMN_DESCRIPTION,medicine.getDescription());
        values.put(DatabaseHelper.MEDICINES_COLUMN_IMAGE, medicine.getImage());

        long insertId = database.insert(DatabaseHelper.TABLE_MEDICINES, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICINES,
                medicineAllColumns, DatabaseHelper.MEDICINES_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MedicineEntity newMedicineEntity = cursorToDetailMedicine(cursor);
        cursor.close();

        closeHelper();
        medicineCreationCallback.onMedicineEntityCreated(newMedicineEntity);
    }

    @Override
    public void saveMedicineEntity(final MedicineEntity medicine, final MedicineSaveCallback medicineSaveCallback) {
        if (medicineSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEDICINES_COLUMN_ID, medicine.getMedicineId());
        values.put(DatabaseHelper.MEDICINES_COLUMN_NAME, medicine.getName());
        values.put(DatabaseHelper.MEDICINES_COLUMN_FIRSTDAY, medicine.getFirstDay());
        values.put(DatabaseHelper.MEDICINES_COLUMN_FIRSTHOUR, medicine.getFirstHour());
        values.put(DatabaseHelper.MEDICINES_COLUMN_LASTDAY,medicine.getLastDay());
        values.put(DatabaseHelper.MEDICINES_COLUMN_LASTHOUR,medicine.getLastHour());
        values.put(DatabaseHelper.MEDICINES_COLUMN_INTERVAL,medicine.getInterval());
        values.put(DatabaseHelper.MEDICINES_COLUMN_DESCRIPTION,medicine.getDescription());
        values.put(DatabaseHelper.MEDICINES_COLUMN_IMAGE, medicine.getImage());
        openHelper();

        database.update(DatabaseHelper.TABLE_MEDICINES, values, DatabaseHelper.MEDICINES_COLUMN_ID + " = ?", new String[]{String.valueOf(medicine.getMedicineId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICINES,
                medicineAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        MedicineEntity medicineEntity = null;
        while (!cursor.isAfterLast()) {
            medicineEntity = cursorToDetailMedicine(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        medicineSaveCallback.onMedicineEntitySaved(medicineEntity);
    }

    @Override
    public void deleteMedicineEntity(int medicineId, MedicineDeletionCallback medicineDeletionCallback) {
        if (medicineDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_MEDICINES, DatabaseHelper.MEDICINES_COLUMN_ID
                + " = " + medicineId, null);
        Collection<MedicineEntity> medicinesCollection = new ArrayList<MedicineEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICINES,
                medicineListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MedicineEntity medicine = cursorToListItemMedicine(cursor);
            medicinesCollection.add(medicine);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        medicineDeletionCallback.onMedicineEntityDeleted(medicinesCollection);
    }

    private MedicineEntity cursorToListItemMedicine(Cursor cursor) {
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineId(cursor.getInt(0));
        medicine.setName(cursor.getString(1));
        return medicine;
    }
    //private final MedicineEntityJsonMapper medicineEntityJsonMapper;

    private MedicineEntity cursorToDetailMedicine(Cursor cursor) {
        MedicineEntity medicine = new MedicineEntity();
        medicine.setMedicineId(cursor.getInt(0));
        medicine.setName(cursor.getString(1));
        medicine.setFirstDay(cursor.getString(2));
        medicine.setFirstHour(cursor.getString(3));
        medicine.setLastDay(cursor.getString(4));
        medicine.setLastHour(cursor.getString(5));
        medicine.setInterval(cursor.getString(6));
        medicine.setDescription(cursor.getString(7));
        medicine.setImage(cursor.getString(8));
        return medicine;
    }
}
