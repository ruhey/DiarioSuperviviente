package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.MeetingEntity;

import java.util.ArrayList;
import java.util.Collection;

public class MeetingDatabaseAPIImpl implements MeetingDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] meetingListColumns = {DatabaseHelper.MEETINGS_COLUMN_ID,
            DatabaseHelper.MEETINGS_COLUMN_NAME};



    private String[] meetingAllColumns = {DatabaseHelper.MEETINGS_COLUMN_ID,
            DatabaseHelper.MEETINGS_COLUMN_NAME,
            DatabaseHelper.MEETINGS_COLUMN_PLACE,
            DatabaseHelper.MEETINGS_COLUMN_QUESTIONS,
            DatabaseHelper.MEETINGS_COLUMN_DATEMEETING,
            DatabaseHelper.MEETINGS_COLUMN_HOURMEETING,
            DatabaseHelper.MEETINGS_COLUMN_DATEALARM,
            DatabaseHelper.MEETINGS_COLUMN_HOURALARM,
            DatabaseHelper.MEETINGS_COLUMN_DURATION,
            DatabaseHelper.MEETINGS_COLUMN_IMAGE,
            DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID,
            DatabaseHelper.MEETINGS_COLUMN_MEDICATION_ID,
            DatabaseHelper.MEETINGS_COLUMN_TEST_ID,
            DatabaseHelper.MEETINGS_COLUMN_SYMPTHOM_ID};

    public MeetingDatabaseAPIImpl(Context context) {
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
    public void getMeetingEntityList(MeetingListCallback meetingListCallback) {
        if (meetingListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<MeetingEntity> meetingsCollection = new ArrayList<MeetingEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MeetingEntity meeting = cursorToListItemMeeting(cursor);
            meetingsCollection.add(meeting);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        meetingListCallback.onMeetingListLoaded(meetingsCollection);
    }

    @Override
    public void getMeetingEntityById(int meetingId, MeetingDetailsCallback meetingDetailsCallback) {
        if (meetingDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        MeetingEntity meetingEntity = null;
        while (!cursor.isAfterLast()) {
            meetingEntity = cursorToDetailMeeting(cursor);
            if(meetingEntity.getMeetingId()==meetingId) {
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        meetingDetailsCallback.onMeetingEntityLoaded(meetingEntity);
        //MeetingEntity meetingEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createMeetingEntity(MeetingEntity meeting, MeetingCreationCallback meetingCreationCallback) {
        if (meetingCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEETINGS_COLUMN_NAME, meeting.getName());
        values.put(DatabaseHelper.MEETINGS_COLUMN_PLACE, meeting.getPlace());
        values.put(DatabaseHelper.MEETINGS_COLUMN_QUESTIONS, meeting.getQuestions());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATEMEETING, meeting.getDateMeeting());
        values.put(DatabaseHelper.MEETINGS_COLUMN_HOURMEETING, meeting.getHourMeeting());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATEALARM, meeting.getDateAlarm());
        values.put(DatabaseHelper.MEETINGS_COLUMN_HOURALARM, meeting.getHourAlarm());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DURATION, meeting.getDuration());
        values.put(DatabaseHelper.MEETINGS_COLUMN_IMAGE, meeting.getImage());
        values.put(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID, meeting.getContactId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_MEDICATION_ID, meeting.getMedicationId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_TEST_ID, meeting.getTestId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_SYMPTHOM_ID, meeting.getSympthomId());

        long insertId = database.insert(DatabaseHelper.TABLE_MEETINGS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingAllColumns, DatabaseHelper.MEETINGS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MeetingEntity newMeetingEntity = cursorToDetailMeeting(cursor);
        cursor.close();

        closeHelper();
        meetingCreationCallback.onMeetingEntityCreated(newMeetingEntity);
    }

    @Override
    public void saveMeetingEntity(final MeetingEntity meeting, final MeetingSaveCallback meetingSaveCallback) {
        if (meetingSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MEETINGS_COLUMN_ID, meeting.getMeetingId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_NAME, meeting.getName());
        values.put(DatabaseHelper.MEETINGS_COLUMN_PLACE, meeting.getPlace());
        values.put(DatabaseHelper.MEETINGS_COLUMN_QUESTIONS, meeting.getQuestions());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATEMEETING, meeting.getDateMeeting());
        values.put(DatabaseHelper.MEETINGS_COLUMN_HOURMEETING, meeting.getHourMeeting());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DATEALARM, meeting.getDateAlarm());
        values.put(DatabaseHelper.MEETINGS_COLUMN_HOURALARM, meeting.getHourAlarm());
        values.put(DatabaseHelper.MEETINGS_COLUMN_DURATION, meeting.getDuration());
        values.put(DatabaseHelper.MEETINGS_COLUMN_IMAGE, meeting.getImage());
        values.put(DatabaseHelper.MEETINGS_COLUMN_CONTACT_ID, meeting.getContactId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_MEDICATION_ID, meeting.getMedicationId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_TEST_ID, meeting.getTestId());
        values.put(DatabaseHelper.MEETINGS_COLUMN_SYMPTHOM_ID, meeting.getSympthomId());
        openHelper();

        database.update(DatabaseHelper.TABLE_MEETINGS, values, DatabaseHelper.MEETINGS_COLUMN_ID + " = ?", new String[]{String.valueOf(meeting.getMeetingId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        MeetingEntity meetingEntity = null;
        while (!cursor.isAfterLast()) {
            meetingEntity = cursorToDetailMeeting(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        meetingSaveCallback.onMeetingEntitySaved(meetingEntity);
    }

    @Override
    public void deleteMeetingEntity(int meetingId, MeetingDeletionCallback meetingDeletionCallback) {
        if (meetingDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_MEETINGS, DatabaseHelper.MEETINGS_COLUMN_ID
                + " = " + meetingId, null);
        Collection<MeetingEntity> meetingsCollection = new ArrayList<MeetingEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MeetingEntity meeting = cursorToListItemMeeting(cursor);
            meetingsCollection.add(meeting);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        meetingDeletionCallback.onMeetingEntityDeleted(meetingsCollection);
    }

    private MeetingEntity cursorToListItemMeeting(Cursor cursor) {
        MeetingEntity meeting = new MeetingEntity();
        meeting.setMeetingId(cursor.getInt(0));
        meeting.setName(cursor.getString(1));
        return meeting;
    }
    //private final MeetingEntityJsonMapper meetingEntityJsonMapper;

    private MeetingEntity cursorToDetailMeeting(Cursor cursor) {
        MeetingEntity meeting = new MeetingEntity();
        meeting.setMeetingId(cursor.getInt(0));
        meeting.setName(cursor.getString(1));
        meeting.setPlace(cursor.getString(2));
        meeting.setQuestions(cursor.getString(3));
        meeting.setDateMeeting(cursor.getString(4));
        meeting.setHourMeeting(cursor.getString(5));
        meeting.setDateAlarm(cursor.getString(6));
        meeting.setHourAlarm(cursor.getString(7));
        meeting.setDuration(cursor.getString(8));
        meeting.setImage(cursor.getString(9));

        meeting.setContactId(cursor.getString(10));
        meeting.setMedicationId(cursor.getString(11));
        meeting.setSympthomId(cursor.getString(12));
        meeting.setTestId(cursor.getString(13));
        return meeting;
    }
}
