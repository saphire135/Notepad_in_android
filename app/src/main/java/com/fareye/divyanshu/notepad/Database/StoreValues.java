package com.fareye.divyanshu.notepad.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diyanshu on 10/8/17.
 */

public class StoreValues extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String NOTES_TABLE_NAME = "notes";
    public static final String NOTES_ID = "id";
    public static final String NOTES_TITLE = "title";
    public static final String NOTES_TIME = "createdon";
    public static final String NOTES_SIZE = "size";

    public StoreValues(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NOTES_TABLE_NAME + "(" +
                NOTES_ID + " INTEGER PRIMARY KEY, " +
                NOTES_TITLE + " TEXT, " +
                NOTES_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                NOTES_SIZE + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertNotes (String title, int size) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_TIME, "datetime('now', 'localtime')");
        contentValues.put(NOTES_SIZE, size);
        db.insert(NOTES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateNotes(Integer id, String title, String time, int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_TITLE, title);
        contentValues.put(NOTES_TIME, time);
        contentValues.put(NOTES_SIZE, size);
        db.update(NOTES_TABLE_NAME, contentValues, NOTES_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Cursor getNotes(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + NOTES_TABLE_NAME + " WHERE " +
                NOTES_ID + "=?", new String[] { Integer.toString(id) } );
        return res;
    }
    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + NOTES_TABLE_NAME, null );
        return res;
    }

    public Integer deleteNotes(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTES_TABLE_NAME,
                NOTES_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

}
