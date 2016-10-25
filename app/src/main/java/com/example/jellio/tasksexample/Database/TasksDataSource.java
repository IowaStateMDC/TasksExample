package com.example.jellio.tasksexample.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jellio.tasksexample.Database.SQLiteHelper;
import com.example.jellio.tasksexample.Task;

import java.util.ArrayList;

/**
 * Created by jellio on 10/25/16.
 */

public class TasksDataSource {

    /*** The database and the database helper ***/
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    /*** The columns associated with the table **/
    private String[] allColumns = {
            SQLiteHelper.COLUMN_ID,          // long 0
            SQLiteHelper.COLUMN_NAME         // test not null
    };

    /**
     * Initialize a new WorkoutDataSource with the given context.
     * @param context The context this object is running in.
     */
    public TasksDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    /**
     * Opens the database for usage.
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Closes the database.
     * Please call this every time you upen the database.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Creates a task in the database based off the given task.
     * @param task The taskto insert into the database.
     */
    public void createTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NAME, task.getName());
        database.insert(SQLiteHelper.TABLE_TASKS, null, values);
    }

    /**
     * Removes a task from the database.
     * @param task The workout to be deleted. (This is based off the ID.)
     */
    public void deleteTask(Task task) {
        database.delete(SQLiteHelper.TABLE_TASKS,
                SQLiteHelper.COLUMN_ID + " = " + task.get_id(), null);
    }

    /**
     * Get all the tasks.
     * @return A list of workouts for the given date.
     */
    public ArrayList<Task> getTasks() {
        // List of workouts to return
        ArrayList<Task> tasks = new ArrayList<>();

        // Cursor is what moves throughout the entire database
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS,
                allColumns, null, null, null, null,
                SQLiteHelper.COLUMN_ID + " ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);

            tasks.add(task);

            cursor.moveToNext();
        }
        cursor.close();

        return tasks;
    }

    /**
     * Converts the current cursor object to a workout.
     * @param cursor The cursor (what the database is pointing at).
     * @return A new workout from the given cursor.
     */
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task(cursor.getString(1));
        task.set_id(cursor.getLong(0));
        return task;
    }
}
