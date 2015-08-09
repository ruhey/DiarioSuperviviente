package org.aecc.superdiary.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS = "contacts";
    public static final String CONTACTS_COLUMN_ID = "_id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_SURNAME = "surname";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_IMAGE = "image";
    public static final String CONTACTS_COLUMN_CATEGORY = "category";

    private static final String DATABASE_NAME = "superdiary";
    private static final int DATABASE_VERSION = 1;

    private static final String CONTACTS_CREATE = TABLE_CONTACTS
            + "(" + CONTACTS_COLUMN_ID + " integer primary key autoincrement, "
            + CONTACTS_COLUMN_NAME + " text not null,"
            + CONTACTS_COLUMN_SURNAME + " text not null,"
            + CONTACTS_COLUMN_PHONE + " text not null,"
            + CONTACTS_COLUMN_EMAIL + " text not null,"
            + CONTACTS_COLUMN_IMAGE + " text not null,"
            + CONTACTS_COLUMN_CATEGORY + " text not null,"
            + " text not null);";;

    private static final String DATABASE_CREATE = "create table "
            + CONTACTS_CREATE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(database);
    }
}
