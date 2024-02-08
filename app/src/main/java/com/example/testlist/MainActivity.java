package com.example.testlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


// TODO Liste                                   "Done"
// TODO Oprette nye og slette og redigere       "Done"
// TODO Gemmes på enheden                       "Done"
// TODO Checkbox (Done)                         "Done"
// TODO Tid oprettet
// TODO Tid planlagt
// TODO Type                                    "Done"
// TODO Beskrivelse                             "Done"
// TODO Icon
// TODO Notification
// TODO Alarm med lyd
// TODO Repeatable?
// TODO Responsive Design
// TODO
// TODO



public class MainActivity extends AppCompatActivity implements TaskAdapter.EditTaskListener {

    private List<Task> taskList;
    private ListView taskListView;
    private TaskAdapter taskAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();
        taskListView = findViewById(R.id.taskListView);

        taskAdapter = new TaskAdapter(this, taskList);
        taskAdapter.setEditTaskListener(this);

        taskListView.setAdapter(taskAdapter);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        loadTasks();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        for (Task task : taskList) {
            Log.d("TaskDebug", "Task: " + task.getName() + ", Description: " + task.getDescription());
        }

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTask(position);
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Ensure that the position is within the valid range before calling deleteTask
                if (position >= 0 && position < taskList.size()) {
                    deleteTask(position);
                }
                return true;
            }
        });
    }



    private void addTask() {
        EditText taskEditText = findViewById(R.id.taskEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        EditText categoryEditText = findViewById(R.id.categoryEditText);

        String taskName = taskEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim();

        Log.d("TaskDebug", "TaskName: " + taskName + ", Description: " + description + ", Category: " + category);

        if (!taskName.isEmpty()) {
            Task newTask = new Task(taskName, description, category);
            taskList.add(newTask);
            taskAdapter.notifyDataSetChanged(); // Notify adapter of data change
            saveTasks();
            taskEditText.getText().clear();
            descriptionEditText.getText().clear();
            categoryEditText.getText().clear();
        }
    }

    @Override
    public void onEditTask(int position) {
        editTask(position);
    }


    public void editTask(final int position) {
        // Get the task at the specified position
        Task taskToEdit = taskList.get(position);

        // Create an AlertDialog with a custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        // Inflate a custom layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.edit_task_dialog, null);
        builder.setView(view);

        // Get references to EditTexts in the custom layout
        EditText nameEditText = view.findViewById(R.id.editNameEditText);
        EditText descriptionEditText = view.findViewById(R.id.editDescriptionEditText);
        EditText categoryEditText = view.findViewById(R.id.editCategoryEditText);

        // Set the current values of the task in the EditTexts
        nameEditText.setText(taskToEdit.getName());
        descriptionEditText.setText(taskToEdit.getDescription());
        categoryEditText.setText(taskToEdit.getCategory());

        // Set up the positive button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the new values from the EditTexts
                String newName = nameEditText.getText().toString().trim();
                String newDescription = descriptionEditText.getText().toString().trim();
                String newCategory = categoryEditText.getText().toString().trim();

                // Update the task in the list
                taskToEdit.setName(newName);
                taskToEdit.setDescription(newDescription);
                taskToEdit.setCategory(newCategory);

                // Notify adapter of data change
                taskAdapter.notifyDataSetChanged();

                // Save the updated tasks
                saveTasks();
            }
        });

        // Set up the negative button click listener (optional)
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle cancellation if needed
            }
        });

        // Show the dialog
        builder.show();
    }

    public void deleteTask(int position) {
        // Your code for deleting a task
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            taskAdapter.notifyDataSetChanged(); // Notify adapter of data change
            saveTasks();
        } else {
            Log.e("MainActivity", "Invalid index when trying to delete task");
        }
    }

    private void saveTasks() {
        // Save tasks to SharedPreferences
        Gson gson = new Gson();
        String tasksJson = gson.toJson(taskList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", tasksJson);
        editor.apply();
    }

    private void loadTasks() {
        // Load tasks from SharedPreferences
        String tasksJson = sharedPreferences.getString("tasks", null);

        if (tasksJson != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Task>>() {}.getType();
            taskList = gson.fromJson(tasksJson, type);
            taskAdapter.clear();
            taskAdapter.addAll(taskList);
            taskAdapter.notifyDataSetChanged();
        }
    }
}