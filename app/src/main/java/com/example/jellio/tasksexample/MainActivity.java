package com.example.jellio.tasksexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jellio.tasksexample.Database.TasksDataSource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*** The activity context (never know when you need it) ***/
    private SharedPreferences prefs;
    private Context context;

    /*** Views used in our activity ***/
    private EditText etNewTask;
    private Button btnNewTask;
    private ListView lvTasks;

    /*** Our database information ***/
    private ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String nameString = prefs.getString("name", "Default");
        setTitle(nameString);
        this.context = this;

        /*** Get the tasks saved in memory (hard coded for now) ***/
        final TasksDataSource db = new TasksDataSource(this);
        db.open();
        tasks = db.getTasks();
        db.close();

        /*** Get the views for the activity ***/
        etNewTask = (EditText) findViewById(R.id.activity_main_edit_text_new_task);
        btnNewTask = (Button) findViewById(R.id.activity_main_button_new_task);
        lvTasks = (ListView) findViewById(R.id.activity_main_list_view_tasks);

        /*** Tell the "done" button to add a new tasks when it's pressed ***/
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** If the user actually put something into the text field ***/
                if (!etNewTask.getText().toString().isEmpty()) {
                    /*** Add a new task to the list view ***/
                    Task newTask = new Task(etNewTask.getText().toString());
                    tasks.add(newTask);

                    /*** Add the task to the database ***/
                    db.open();
                    db.createTask(newTask);
                    db.close();

                    /*** Tell the list view to update itself ***/
                    ((TasksListViewAdapter) lvTasks.getAdapter()).notifyDataSetChanged();

                    /*** Clear the current edit text (make it empty) ***/
                    etNewTask.setText("");
                }

            }
        });

        lvTasks.setAdapter(new TasksListViewAdapter(this, tasks));
        Button setName = (Button) findViewById(R.id.buttonSetName);
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SetNameActivity.class);
                startActivity(i);
            }
        });
    }
}
