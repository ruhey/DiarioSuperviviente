package org.aecc.superdiary.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.ContactEntity;

import java.util.ArrayList;
import java.util.Collection;

public class ContactDatabaseAPIImpl implements ContactDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] contactListColumns = {DatabaseHelper.CONTACTS_COLUMN_ID,
            DatabaseHelper.CONTACTS_COLUMN_NAME};
    private String[] contactAllColumns = {DatabaseHelper.CONTACTS_COLUMN_ID,
            DatabaseHelper.CONTACTS_COLUMN_NAME,
            DatabaseHelper.CONTACTS_COLUMN_SURNAME,
            DatabaseHelper.CONTACTS_COLUMN_EMAIL,
            DatabaseHelper.CONTACTS_COLUMN_PHONE,
            DatabaseHelper.CONTACTS_COLUMN_IMAGE,
            DatabaseHelper.CONTACTS_COLUMN_CATEGORY};

    public ContactDatabaseAPIImpl(Context context) {
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
    public void getContactEntityList(ContactListCallback contactListCallback) {
        if (contactListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<ContactEntity> contactsCollection = new ArrayList<ContactEntity>();

        openHelper();
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
        closeHelper();
        contactListCallback.onContactListLoaded(contactsCollection);
    }

    @Override
    public void getContactEntityById(int contactId, ContactDetailsCallback contactDetailsCallback) {
        if (contactDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        String[] whereArgs = new String[] {
                String.valueOf(contactId)
        };
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS,
                contactAllColumns, "_id = ?", whereArgs, null, null, null);

        cursor.moveToFirst();
        ContactEntity contactEntity = null;
        while (!cursor.isAfterLast()) {
            contactEntity = cursorToDetailContact(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        contactDetailsCallback.onContactEntityLoaded(contactEntity);
        //ContactEntity contactEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createContactEntity(ContactEntity contact, ContactCreationCallback contactCreationCallback) {
        if (contactCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CONTACTS_COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.CONTACTS_COLUMN_SURNAME, contact.getSurname());
        values.put(DatabaseHelper.CONTACTS_COLUMN_PHONE, contact.getPhone());
        values.put(DatabaseHelper.CONTACTS_COLUMN_EMAIL, contact.getEmail());
        values.put(DatabaseHelper.CONTACTS_COLUMN_IMAGE, contact.getImage());
        values.put(DatabaseHelper.CONTACTS_COLUMN_CATEGORY, contact.getCategoryType());

        long insertId = database.insert(DatabaseHelper.TABLE_CONTACTS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS,
                contactAllColumns, DatabaseHelper.CONTACTS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ContactEntity newContactEntity = cursorToDetailContact(cursor);
        cursor.close();

        closeHelper();
        contactCreationCallback.onContactEntityCreated(newContactEntity);
    }

    @Override
    public void saveContactEntity(final ContactEntity contact, final ContactSaveCallback contactSaveCallback) {
        if (contactSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CONTACTS_COLUMN_ID, contact.getContactId());
        values.put(DatabaseHelper.CONTACTS_COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.CONTACTS_COLUMN_SURNAME, contact.getSurname());
        values.put(DatabaseHelper.CONTACTS_COLUMN_PHONE, contact.getPhone());
        values.put(DatabaseHelper.CONTACTS_COLUMN_EMAIL, contact.getEmail());
        values.put(DatabaseHelper.CONTACTS_COLUMN_IMAGE, contact.getImage());
        values.put(DatabaseHelper.CONTACTS_COLUMN_CATEGORY, contact.getCategoryType());
        openHelper();

        database.update(DatabaseHelper.TABLE_CONTACTS, values, DatabaseHelper.CONTACTS_COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getContactId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_CONTACTS,
                contactAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        ContactEntity contactEntity = null;
        while (!cursor.isAfterLast()) {
            contactEntity = cursorToDetailContact(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        contactSaveCallback.onContactEntitySaved(contactEntity);
    }

    @Override
    public void deleteContactEntity(int contactId, ContactDeletionCallback contactDeletionCallback) {
        if (contactDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_CONTACTS, DatabaseHelper.CONTACTS_COLUMN_ID
                + " = " + contactId, null);
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
        closeHelper();
        contactDeletionCallback.onContactEntityDeleted(contactsCollection);
    }

    private ContactEntity cursorToListItemContact(Cursor cursor) {
        ContactEntity contact = new ContactEntity();
        contact.setContactId(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        return contact;
    }
    //private final ContactEntityJsonMapper contactEntityJsonMapper;


    private ContactEntity cursorToDetailContact(Cursor cursor) {
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
