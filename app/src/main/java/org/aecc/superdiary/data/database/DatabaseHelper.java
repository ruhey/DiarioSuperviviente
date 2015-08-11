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

    public static final String TABLE_COMMENTS = "comments";
    public static final String COMMENTS_COLUMN_ID = "_id";
    public static final String COMMENTS_COLUMN_DATEPOSTED = "datePosted";
    public static final String COMMENTS_COLUMN_CONTENT = "content";
    public static final String COMMENTS_COLUMN_DISCARDED = "discarded";
    public static final String COMMENTS_COLUMN_REMINDER = "reminder";
    public static final String COMMENTS_COLUMN_TIMESELAPSED = "timesElapsed";

    private static final String DATABASE_NAME = "superdiary";
    private static final int DATABASE_VERSION = 1;

    private static final String CONTACTS_CREATE = " create table (" + TABLE_CONTACTS
            + "(" + CONTACTS_COLUMN_ID + " integer primary key autoincrement, "
            + CONTACTS_COLUMN_NAME + " text not null,"
            + CONTACTS_COLUMN_SURNAME + " text not null,"
            + CONTACTS_COLUMN_PHONE + " text not null,"
            + CONTACTS_COLUMN_EMAIL + " text not null,"
            + CONTACTS_COLUMN_IMAGE + " text not null,"
            + CONTACTS_COLUMN_CATEGORY + " text not null);";

    private static final String COMMENTS_CREATE = " create table (" + TABLE_COMMENTS
            + "(" + COMMENTS_COLUMN_ID + " integer primary key autoincrement, "
            + COMMENTS_COLUMN_DATEPOSTED + " text not null,"
            + COMMENTS_COLUMN_CONTENT + " text not null,"
            + COMMENTS_COLUMN_DISCARDED + " text not null,"
            + COMMENTS_COLUMN_REMINDER + " text not null,"
            + COMMENTS_COLUMN_TIMESELAPSED + " text not null);";

    private static final String DATABASE_CREATE = COMMENTS_CREATE
            + CONTACTS_CREATE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(CONTACTS_CREATE);
        database.execSQL(COMMENTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(database);
    }
}
