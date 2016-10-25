package com.example.jellio.tasksexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jellio.tasksexample.Database.TasksDataSource;

import java.util.ArrayList;

/**
 * Created by jellio on 10/4/16.
 */

public class TasksListViewAdapter extends BaseAdapter {

    /*** You never know when you'll need it :) ***/
    private Context context;

    private LayoutInflater layoutInflater;
    private ArrayList<Task> tasks;

    public TasksListViewAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*** Get the task at this view's position ***/
        final Task task = tasks.get(position);

        /*** If the convertView is null (aka the View hasn't already been created) then create one ***/
        /*** This is an efficient way of populating the list view ***/
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.tasks_list_view_item, parent, false);
        }

        /*** Get the views that are inside the list view's current item ***/
        Button btnDone = (Button) convertView.findViewById(R.id.tasks_list_view_item_button_done);
        TextView txtName = (TextView) convertView.findViewById(R.id.tasks_list_view_item_text_view_name);

        /*** Tell the "done" button to do something! (Remove the current task) ***/
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*** Remove the task from the database ***/
                TasksDataSource db = new TasksDataSource(context);
                db.open();
                db.deleteTask(task);
                db.close();

                /*** Remove the task from the array list ***/
                tasks.remove(task);

                /*** This is to update the adapter (basically tells it that its data has changed) ***/
                notifyDataSetChanged();
            }
        });

        /*** Take the current "task" and set the view's text to that task's name ***/
        txtName.setText(task.getName());

        /*** Return the view! This is done for every single item in the list view ***/
        return convertView;
    }
}
