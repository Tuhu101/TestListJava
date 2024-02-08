package com.example.testlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView taskTextView = convertView.findViewById(R.id.taskTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        CheckBox taskCheckBox = convertView.findViewById(R.id.taskCheckBox);

        taskTextView.setText(task.getName());
        descriptionTextView.setText(task.getDescription()); // Set description
        taskCheckBox.setChecked(task.isCompleted());

        // Set a listener for the entire item layout
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Handle long click (delete)
                ((MainActivity) getContext()).deleteTask(position);
                return true;
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click (edit)
                ((MainActivity) getContext()).editTask(position);
            }
        });

        // Set a listener for the checkbox
        taskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompleted(isChecked);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
