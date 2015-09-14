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

    public static final String TABLE_MEDICINES = "medicines";
    public static final String MEDICINES_COLUMN_ID = "_id";
    public static final String MEDICINES_COLUMN_NAME = "name";
    public static final String MEDICINES_COLUMN_FIRSTDAY = "firstDay";
    public static final String MEDICINES_COLUMN_FIRSTHOUR = "firstHour";
    public static final String MEDICINES_COLUMN_LASTDAY = "lastDay";
    public static final String MEDICINES_COLUMN_LASTHOUR = "lastHour";
    public static final String MEDICINES_COLUMN_INTERVAL = "interval";
    public static final String MEDICINES_COLUMN_DESCRIPTION = "description";
    public static final String MEDICINES_COLUMN_IMAGE = "image";



    public static final String TABLE_MEETINGS = "meetings";
    public static final String MEETINGS_COLUMN_ID = "_id";
    public static final String MEETINGS_COLUMN_NAME = "name";
    public static final String MEETINGS_COLUMN_PLACE = "place";
    public static final String MEETINGS_COLUMN_QUESTIONS = "questions";
    public static final String MEETINGS_COLUMN_DATEMEETING = "dateMeeting";
    public static final String MEETINGS_COLUMN_HOURMEETING = "hourMeeting";
    public static final String MEETINGS_COLUMN_DATEALARM = "dateAlarm";
    public static final String MEETINGS_COLUMN_HOURALARM = "hourAlarm";
    public static final String MEETINGS_COLUMN_DURATION = "duration";
    public static final String MEETINGS_COLUMN_CONTACT_ID = "contact_id";
    public static final String MEETINGS_COLUMN_MEDICATION_ID = "medication_id";
    public static final String MEETINGS_COLUMN_TEST_ID = "test_id";
    public static final String MEETINGS_COLUMN_SYMPTHOM_ID = "sympthom_id";
    public static final String MEETINGS_COLUMN_IMAGE = "image";

    public static final String TABLE_ROUTINES = "routines";
    public static final String ROUTINES_COLUMN_ID = "_id";
    public static final String ROUTINES_COLUMN_NAME = "name";
    public static final String ROUTINES_COLUMN_PLACE = "place";
    public static final String ROUTINES_COLUMN_DESCRIPTION = "description";
    public static final String ROUTINES_COLUMN_DATEROUTINE = "dateRoutine";
    public static final String ROUTINES_COLUMN_HOURROUTINE = "hourRoutine";
    public static final String ROUTINES_COLUMN_DATEALARM = "dateAlarm";
    public static final String ROUTINES_COLUMN_HOURALARM = "hourAlarm";
    public static final String ROUTINES_COLUMN_DURATION = "duration";
    public static final String ROUTINES_COLUMN_SATISFACTION = "satisfaction";
    public static final String ROUTINES_COLUMN_IMAGE = "image";

    public static final String TABLE_SYMPTOMS = "symptoms";
    public static final String SYMPTOMS_COLUMN_ID = "_id";
    public static final String SYMPTOMS_COLUMN_NAME = "name";
    public static final String SYMPTOMS_COLUMN_DATE = "dateSymptom";
    public static final String SYMPTOMS_COLUMN_HOUR = "hourSymptom";
    public static final String SYMPTOMS_COLUMN_DESCRIPTION = "description";
    public static final String SYMPTOMS_COLUMN_IMAGE = "image";

    public static final String TABLE_EXAMS = "examinations";
    public static final String EXAMS_COLUMN_ID = "_id";
    public static final String EXAMS_COLUMN_NAME = "name";
    public static final String EXAMS_COLUMN_DATE = "dateExam";
    public static final String EXAMS_COLUMN_HOUR = "hourExam";
    public static final String EXAMS_COLUMN_DESCRIPTION = "description";
    public static final String EXAMS_COLUMN_IMAGE = "image";

    public static final String TABLE_COMMENTS = "comments";
    public static final String COMMENTS_COLUMN_ID = "_id";
    public static final String COMMENTS_COLUMN_DATEPOSTED = "datePosted";
    public static final String COMMENTS_COLUMN_CONTENT = "content";
    public static final String COMMENTS_COLUMN_DISCARDED = "discarded";
    public static final String COMMENTS_COLUMN_REMINDER = "reminder";
    public static final String COMMENTS_COLUMN_TIMESELAPSED = "timesElapsed";

    private static final String DATABASE_NAME = "superdiary";
    private static final int DATABASE_VERSION = 6;
    private static SQLiteDatabase.CursorFactory factory = null;


    private static final String CONTACTS_CREATE = " create table " + TABLE_CONTACTS
            + " (" + CONTACTS_COLUMN_ID + " integer primary key autoincrement, "
            + CONTACTS_COLUMN_NAME + " text not null,"
            + CONTACTS_COLUMN_SURNAME + " text not null,"
            + CONTACTS_COLUMN_PHONE + " text not null,"
            + CONTACTS_COLUMN_EMAIL + " text not null,"
            + CONTACTS_COLUMN_IMAGE + " text,"
            + CONTACTS_COLUMN_CATEGORY + " text );";

    private static final String MEDICINES_CREATE = " create table " + TABLE_MEDICINES
            + " (" + MEDICINES_COLUMN_ID + " integer primary key autoincrement, "
            + MEDICINES_COLUMN_NAME + " text not null,"
            + MEDICINES_COLUMN_FIRSTDAY + " text not null,"
            + MEDICINES_COLUMN_FIRSTHOUR + " text not null,"
            + MEDICINES_COLUMN_LASTDAY + " text not null,"
            + MEDICINES_COLUMN_LASTHOUR + " text not null,"
            + MEDICINES_COLUMN_INTERVAL + " text not null default '0',"
            + MEDICINES_COLUMN_DESCRIPTION + " text not null,"
            + MEDICINES_COLUMN_IMAGE + " text);";

    private static final String MEETINGS_CREATE = " create table " + TABLE_MEETINGS
            + " (" + MEETINGS_COLUMN_ID + " integer primary key autoincrement, "
            + MEETINGS_COLUMN_NAME + " text not null,"
            + MEETINGS_COLUMN_PLACE + " text not null,"
            + MEETINGS_COLUMN_QUESTIONS + " text not null,"
            + MEETINGS_COLUMN_DATEMEETING + " text not null,"
            + MEETINGS_COLUMN_HOURMEETING + " text not null,"
            + MEETINGS_COLUMN_DATEALARM + " text not null,"
            + MEETINGS_COLUMN_HOURALARM + " text not null,"
            + MEETINGS_COLUMN_DURATION + " text,"
            + MEETINGS_COLUMN_CONTACT_ID + " text,"
            + MEETINGS_COLUMN_MEDICATION_ID + " text,"
            + MEETINGS_COLUMN_TEST_ID + " text,"
            + MEETINGS_COLUMN_SYMPTHOM_ID + " text,"
            + MEETINGS_COLUMN_IMAGE + " text);";

    private static final String ROUTINES_CREATE = " create table " + TABLE_ROUTINES
            + " (" + ROUTINES_COLUMN_ID + " integer primary key autoincrement, "
            + ROUTINES_COLUMN_NAME + " text not null,"
            + ROUTINES_COLUMN_PLACE + " text not null,"
            + ROUTINES_COLUMN_DESCRIPTION + " text not null,"
            + ROUTINES_COLUMN_DATEROUTINE + " text not null,"
            + ROUTINES_COLUMN_HOURROUTINE + " text not null,"
            + ROUTINES_COLUMN_DATEALARM + " text not null,"
            + ROUTINES_COLUMN_HOURALARM + " text not null,"
            + ROUTINES_COLUMN_DURATION + " text not null,"
            + ROUTINES_COLUMN_SATISFACTION + " text not null,"
            + ROUTINES_COLUMN_IMAGE + " text);";

    private static final String EXAMS_CREATE = " create table " + TABLE_EXAMS
            + " (" + EXAMS_COLUMN_ID + " integer primary key autoincrement, "
            + EXAMS_COLUMN_NAME + " text not null,"
            + EXAMS_COLUMN_DATE + " text not null,"
            + EXAMS_COLUMN_HOUR + " text not null,"
            + EXAMS_COLUMN_DESCRIPTION + " text not null,"
            + EXAMS_COLUMN_IMAGE + " text);";

    private static final String SYMPTOMS_CREATE = " create table " + TABLE_SYMPTOMS
            + " (" + SYMPTOMS_COLUMN_ID + " integer primary key autoincrement, "
            + SYMPTOMS_COLUMN_NAME + " text not null,"
            + SYMPTOMS_COLUMN_DATE + " text not null,"
            + SYMPTOMS_COLUMN_HOUR + " text not null,"
            + SYMPTOMS_COLUMN_DESCRIPTION + " text not null,"
            + SYMPTOMS_COLUMN_IMAGE + " text);";

    private static final String COMMENTS_CREATE = " create table " + TABLE_COMMENTS
            + " (" + COMMENTS_COLUMN_ID + " integer primary key autoincrement, "
            + COMMENTS_COLUMN_DATEPOSTED + " text not null,"
            + COMMENTS_COLUMN_CONTENT + " text not null,"
            + COMMENTS_COLUMN_DISCARDED + " text not null,"
            + COMMENTS_COLUMN_REMINDER + " text not null,"
            + COMMENTS_COLUMN_TIMESELAPSED + " text);";

    private static final String DATABASE_CREATE = COMMENTS_CREATE
            + CONTACTS_CREATE + MEETINGS_CREATE + ROUTINES_CREATE + MEDICINES_CREATE + SYMPTOMS_CREATE + EXAMS_CREATE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i(this.getClass().toString(), "Creacion Base de datos " + DATABASE_CREATE);

        database.execSQL(CONTACTS_CREATE);
        database.execSQL(EXAMS_CREATE);
        database.execSQL(COMMENTS_CREATE);
        database.execSQL(MEDICINES_CREATE);
        database.execSQL(ROUTINES_CREATE);
        database.execSQL(MEETINGS_CREATE);
        database.execSQL(SYMPTOMS_CREATE);

        database.execSQL("INSERT INTO " + TABLE_CONTACTS +  " VALUES(null, 'Fernando1', 'Santa Olaya', '686252397', 'a@b.c', 'image', 'pater');");
        database.execSQL("INSERT INTO " + TABLE_CONTACTS +  " VALUES(null, 'Fernando2', 'Santa Olaya', '686252397', 'a@b.c', 'image', 'pater');");
        database.execSQL("INSERT INTO " + TABLE_CONTACTS +  " VALUES(null, 'Fernando3', 'Santa Olaya', '686252397', 'a@b.c', 'image', 'pater');");
        database.execSQL("INSERT INTO " + TABLE_CONTACTS +  " VALUES(null, 'Fernando4', 'Santa Olaya', '686252397', 'a@b.c', 'image', 'pater');");

        //database.execSQL("INSERT INTO " + TABLE_MEETINGS +  " VALUES(null, 'Consulta general', 'Centro médico delicias', 'pedir algo para el dolor de huesos, preguntar por la resonancia, pedir cita para la enfermera', '01-03-2015', '09:00', '27-02-2015', '16:00', '1', 'image');");
        //database.execSQL("INSERT INTO " + TABLE_ROUTINES +  " VALUES(null, 'Partida de Mus', 'Casa de Pedro', 'Una buena partida de cartas siempre viene bien', '01-03-2015', '17:00', '01-03-2015', '16:00', '4','7', 'image');");
        //database.execSQL("INSERT INTO " + TABLE_MEDICINES + " VALUES(null, 'Paracetamol', '01-03-2015', '08:00', '03-03-2015', '16:00','8', 'para los dolores en general', 'image');");
        //database.execSQL("INSERT INTO " + TABLE_EXAMS +     " VALUES(null, 'Radiografia torax', '01-03-2015', '17:00', 'Realizada por unos dolores en el pecho', 'image');");
        //database.execSQL("INSERT INTO " + TABLE_SYMPTOMS +     " VALUES(null, 'Dolor pecho', '10-02-2015', '09:00', 'Dolores en el pecho al toser', 'image');");


        Log.i(this.getClass().toString(), "Tablas creadas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINES);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETINGS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOMS);
        onCreate(database);
    }
}
