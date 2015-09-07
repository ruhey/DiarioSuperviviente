package org.aecc.superdiary.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.domain.AppEntranceElement;

import java.util.ArrayList;
import java.util.Collection;

public class AppEntranceElementDatabaseAPIImpl implements  AppEntranceElementDatabaseAPI{

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    private String[] meetingListColumns = {DatabaseHelper.MEETINGS_COLUMN_ID,
            DatabaseHelper.MEETINGS_COLUMN_PLACE,
            DatabaseHelper.MEETINGS_COLUMN_DATEMEETING,
            DatabaseHelper.MEETINGS_COLUMN_HOURMEETING};

    private String[] routinesListColumns = {DatabaseHelper.ROUTINES_COLUMN_ID,
            DatabaseHelper.ROUTINES_COLUMN_NAME,
            DatabaseHelper.ROUTINES_COLUMN_DATEROUTINE,
            DatabaseHelper.ROUTINES_COLUMN_HOURROUTINE};


    public AppEntranceElementDatabaseAPIImpl(Context context){
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.databaseHelper = new DatabaseHelper(this.context);
    }

    private void openHelper() {
        database = databaseHelper.getWritableDatabase();
    }

    private void closeHelper() {
        databaseHelper.close();
    }

    @Override
    public void getAppEntranceElementList(AppEntranceElementListCallback AppEntranceElementListCallback) {
        if (AppEntranceElementListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<AppEntranceElement> contactsCollection = new ArrayList<AppEntranceElement>();

        openHelper();
        Cursor cursorMeetings = database.query(DatabaseHelper.TABLE_MEETINGS,
                meetingListColumns, null, null, null, null, null);
        Cursor cursorRoutines = database.query(DatabaseHelper.TABLE_ROUTINES,
                routinesListColumns, null, null, null, null, null);

        cursorMeetings.moveToFirst();
        cursorRoutines.moveToFirst();
        for (int i = 1; i < 5; i++){
            AppEntranceElement contact;
            if (cursorMeetings.moveToFirst()){
                if(cursorRoutines.isAfterLast()){
                    break;
                } else {
                    contact = cursorToListItemContact(cursorRoutines);
                    cursorRoutines.moveToNext();
                }
            } else {
                switch(i){
                    case 1:
                        contact = cursorToListItemContact(cursorRoutines);
                        cursorRoutines.moveToNext();
                        break;
                    case 2:
                        contact = cursorToListItemContact(cursorRoutines);
                        cursorRoutines.moveToNext();
                        break;
                    case 3:
                        contact = cursorToListItemContact(cursorRoutines);
                        cursorRoutines.moveToNext();
                        break;
                    case 4:
                        contact = cursorToListItemMeeting(cursorMeetings);
                        cursorMeetings.moveToNext();
                        break;
                    case 5:
                        contact = cursorToListItemContact(cursorRoutines);
                        cursorRoutines.moveToNext();
                        break;
                    default:
                        contact = cursorToListItemContact(cursorRoutines);
                        cursorRoutines.moveToNext();
                        break;
                }
            }
            contactsCollection.add(contact);
        }

        // make sure to close the cursor
        cursorMeetings.close();
        cursorRoutines.close();
        closeHelper();
        AppEntranceElementListCallback.onContactListLoaded(contactsCollection);
    }

    private AppEntranceElement cursorToListItemMeeting(Cursor cursorMeetings) {
        AppEntranceElement element = new AppEntranceElement();
        element.setTextToShow(cursorMeetings.getString(1));
        element.setType("Meeting");
        return element;
    }

    private AppEntranceElement cursorToListItemContact(Cursor cursorRoutines) {

        AppEntranceElement element = new AppEntranceElement();
        element.setTextToShow(cursorRoutines.getString(1));
        element.setType("Rountine");
        return element;
    }
}
