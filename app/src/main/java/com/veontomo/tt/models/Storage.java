package com.veontomo.tt.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;


/**
 * Performs operations with db.
 */
public class Storage extends SQLiteOpenHelper {
    /**
     * current version of the database
     */
    private final static int DB_VERSION = 1;

    /**
     * Name of database that contains tables of the application
     */
    private final static String DB_NAME = "TT";

    public Storage(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // version number 1
        db.execSQL(TT.CREATE_TABLE);
    }

    public static abstract class TTEntry implements BaseColumns {
        public static final String TABLE_NAME = "TongueTwisters";
        public static final String COLUMN_TEXT = "text";
    }


    public static final class TT {
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TTEntry.TABLE_NAME + " (" +
                TTEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TTEntry.COLUMN_TEXT + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE)";
        public static final String SELECT_ALL = "SELECT " + TTEntry._ID
                + ", " + TTEntry.COLUMN_TEXT  + " FROM " + TTEntry.TABLE_NAME + ";";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // empty task: nothing to do
        }
    }

    /**
     * Save tongue-twister in db.
     * @param text tongue-twister text
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long saveTT(String text) {
        ContentValues values = new ContentValues();
        values.put(TTEntry.COLUMN_TEXT, text);
        SQLiteDatabase db = getWritableDatabase();
        long recordId = db.insert(TTEntry.TABLE_NAME, null, values);
        db.close();
        return recordId;
    }

    public List<TongueTwister> retrieveAllTT() {
        List<TongueTwister> all = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(TT.SELECT_ALL, null);
        int colText = cursor.getColumnIndex(TTEntry.COLUMN_TEXT);
        int colId = cursor.getColumnIndex(TTEntry._ID);
        if (cursor.moveToFirst()) {
            TongueTwister tt;
            do {
                tt = new TongueTwister(cursor.getShort(colId), cursor.getString(colText));
                all.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return all;
    }




}
