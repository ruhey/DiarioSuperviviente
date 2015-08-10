package org.aecc.superdiary.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.ContactEntity;
import org.aecc.superdiary.data.net.ApiConnection;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseAPIImpl implements DatabaseAPI{

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] contactListColumns = { DatabaseHelper.CONTACTS_COLUMN_ID,
            DatabaseHelper.CONTACTS_COLUMN_NAME };
    private String[] contactAllColumns = { DatabaseHelper.CONTACTS_COLUMN_ID,
            DatabaseHelper.CONTACTS_COLUMN_NAME,
            DatabaseHelper.CONTACTS_COLUMN_SURNAME,
            DatabaseHelper.CONTACTS_COLUMN_EMAIL,
            DatabaseHelper.CONTACTS_COLUMN_PHONE,
            DatabaseHelper.CONTACTS_COLUMN_IMAGE,
            DatabaseHelper.CONTACTS_COLUMN_CATEGORY};

    private final Context context;

    public DatabaseAPIImpl(Context context) {
        if (context == null ) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        //this.userEntityJsonMapper = userEntityJsonMapper;
    }

    @Override
    public void getContactList(ContactListCallback contactListCallback) {
        if (contactListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<ContactEntity> contactsCollection = new ArrayList<ContactEntity>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS,
                contactListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ContactEntity contact = cursorToListItemContact(cursor);
            contactsCollection.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        contactListCallback.onContactListLoaded(contactsCollection);
    }

    @Override
    public void getContactById(int contactId, ContactDetailsCallback contactDetailsCallback) {
        if (contactDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS,
                contactAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ContactEntity contactEntity = null;
        while (!cursor.isAfterLast()) {
            contactEntity = cursorToDetailContact(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        contactDetailsCallback.onContactEntityLoaded(contactEntity);
        //ContactEntity contactEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    private ContactEntity cursorToDetailContact(Cursor cursor) {
        ContactEntity contact = new ContactEntity();
        contact.setContactId(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        return contact;
    }
    //private final ContactEntityJsonMapper contactEntityJsonMapper;


    private ContactEntity cursorToListItemContact(Cursor cursor) {
        ContactEntity contact = new ContactEntity();
        contact.setContactId(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        contact.setSurname(cursor.getString(2));
        contact.setEmail(cursor.getString(3));
        contact.setPhone(cursor.getString(4));
        contact.setImage(cursor.getString(5));
        contact.setCategoryType(cursor.getString(6));
        return contact;
    }
}
