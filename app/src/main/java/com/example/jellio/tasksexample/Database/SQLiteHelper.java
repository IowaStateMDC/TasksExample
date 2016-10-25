package com.example.jellio.tasksexample.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jellio on 10/25/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    /*** The database file name ***/
    private static final String DATABASE_NAME = "tasks.db";
    /*** The database version ***/
    private static final int DATABASE_VERSION = 1;

    /*** Constants for the "workouts" table ***/
    // Could be protected instead of public
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID   = "_id";
    public static final String COLUMN_NAME = "name";

    /*** Statement to create a table for "workouts" ***/
    private static final String WORKOUT_DATABASE_CREATE = "create table "
            + TABLE_TASKS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(WORKOUT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
