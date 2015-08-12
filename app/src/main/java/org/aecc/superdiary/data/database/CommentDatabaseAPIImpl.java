package org.aecc.superdiary.data.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.aecc.superdiary.data.entity.CommentEntity;

import java.util.ArrayList;
import java.util.Collection;

public class CommentDatabaseAPIImpl implements CommentDatabaseAPI {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] commentListColumns = {DatabaseHelper.COMMENTS_COLUMN_ID,
            DatabaseHelper.COMMENTS_COLUMN_CONTENT, DatabaseHelper.COMMENTS_COLUMN_DISCARDED};
    private String[] commentAllColumns = {DatabaseHelper.COMMENTS_COLUMN_ID,
            DatabaseHelper.COMMENTS_COLUMN_DATEPOSTED,
            DatabaseHelper.COMMENTS_COLUMN_CONTENT,
            DatabaseHelper.COMMENTS_COLUMN_DISCARDED,
            DatabaseHelper.COMMENTS_COLUMN_REMINDER,
            DatabaseHelper.COMMENTS_COLUMN_TIMESELAPSED};

    public CommentDatabaseAPIImpl(Context context) {
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
    public void getCommentEntityList(CommentListCallback commentListCallback) {
        if (commentListCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        Collection<CommentEntity> commentsCollection = new ArrayList<CommentEntity>();

        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                commentListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (Boolean.parseBoolean(cursor.getString(2))) {
                CommentEntity comment = cursorToListItemComment(cursor);
                commentsCollection.add(comment);
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        commentListCallback.onCommentListLoaded(commentsCollection);
    }

    @Override
    public void getCommentEntityById(int commentId, CommentDetailsCallback commentDetailsCallback) {
        if (commentDetailsCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                commentAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        CommentEntity commentEntity = null;
        while (!cursor.isAfterLast()) {
            if (Boolean.parseBoolean(cursor.getString(3))) {
                commentEntity = cursorToDetailComment(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeHelper();
        commentDetailsCallback.onCommentEntityLoaded(commentEntity);
        //CommentEntity commentEntity = this.userEntityJsonMapper.transformUserEntity(responseUserDetails);
    }

    @Override
    public void createCommentEntity(CommentEntity comment, CommentCreationCallback commentCreationCallback) {
        if (commentCreationCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }

        openHelper();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COMMENTS_COLUMN_DATEPOSTED, comment.getDatePosted());
        values.put(DatabaseHelper.COMMENTS_COLUMN_CONTENT, comment.getContent());
        values.put(DatabaseHelper.COMMENTS_COLUMN_DISCARDED, comment.getDiscarded());
        values.put(DatabaseHelper.COMMENTS_COLUMN_REMINDER, comment.getReminder());
        values.put(DatabaseHelper.COMMENTS_COLUMN_TIMESELAPSED, comment.getTimesElapsed());

        long insertId = database.insert(DatabaseHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                commentAllColumns, DatabaseHelper.COMMENTS_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        CommentEntity newCommentEntity = cursorToDetailComment(cursor);
        cursor.close();

        closeHelper();
        commentCreationCallback.onCommentEntityCreated(newCommentEntity);
    }

    @Override
    public void saveCommentEntity(CommentEntity comment, CommentSaveCallback commentSaveCallback) {
        if (commentSaveCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COMMENTS_COLUMN_ID, comment.getCommentId());
        values.put(DatabaseHelper.COMMENTS_COLUMN_DATEPOSTED, comment.getDatePosted());
        values.put(DatabaseHelper.COMMENTS_COLUMN_CONTENT, comment.getContent());
        values.put(DatabaseHelper.COMMENTS_COLUMN_DISCARDED, comment.getDiscarded());
        values.put(DatabaseHelper.COMMENTS_COLUMN_REMINDER, comment.getReminder());
        values.put(DatabaseHelper.COMMENTS_COLUMN_TIMESELAPSED, comment.getTimesElapsed());
        openHelper();

        database.update(DatabaseHelper.TABLE_COMMENTS, values, DatabaseHelper.COMMENTS_COLUMN_ID + " = ?", new String[]{String.valueOf(comment.getCommentId())});
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                commentAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        CommentEntity commentEntity = null;
        while (!cursor.isAfterLast()) {
            commentEntity = cursorToDetailComment(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        closeHelper();
        commentSaveCallback.onCommentEntitySaved(commentEntity);
    }

    @Override
    public void deleteCommentEntity(int commentId, CommentDeletionCallback commentDeletionCallback) {
        if (commentDeletionCallback == null) {
            throw new IllegalArgumentException("Callback cannot be null!!!");
        }
        openHelper();

        database.delete(DatabaseHelper.TABLE_COMMENTS, DatabaseHelper.COMMENTS_COLUMN_ID
                + " = " + commentId, null);
        Collection<CommentEntity> commentsCollection = new ArrayList<CommentEntity>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
                commentListColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CommentEntity comment = cursorToListItemComment(cursor);
            commentsCollection.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        closeHelper();
        commentDeletionCallback.onCommentEntityDeleted(commentsCollection);
    }

    private CommentEntity cursorToDetailComment(Cursor cursor) {
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(cursor.getInt(0));
        comment.setDatePosted(cursor.getString(1));
        comment.setContent(cursor.getString(2));
        comment.setDiscarded(cursor.getString(3));
        comment.setReminder(cursor.getString(4));
        comment.setTimesElapsed(cursor.getString(5));
        return comment;
    }
    //private final CommentEntityJsonMapper commentEntityJsonMapper;


    private CommentEntity cursorToListItemComment(Cursor cursor) {
        CommentEntity comment = new CommentEntity();
        comment.setCommentId(cursor.getInt(0));
        comment.setContent(cursor.getString(1));
        return comment;
    }
}
