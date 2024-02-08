package com.example.testlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends ArrayAdapter<Task> {
    private EditTaskListener editTaskListener;
    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    public interface EditTaskListener {
        void onEditTask(int position);
    }

    public void setEditTaskListener(EditTaskListener listener) {
        this.editTaskListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView categoryTextView = convertView.findViewById(R.id.categoryTextView);
        TextView taskTextView = convertView.findViewById(R.id.taskTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        Button editButton = convertView.findViewById(R.id.editButton);
        CheckBox taskCheckBox = convertView.findViewById(R.id.taskCheckBox);
        ImageView iconImageView = convertView.findViewById(R.id.iconImageView);

        // Display the creation timestamp in a TextView

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (task.getCreatedAt() != null) {
            String formattedDate = dateFormat.format(task.getCreatedAt());
            // Set formattedDate to your TextView or wherever you're using it
            // For example, assuming you have a TextView with id "createdAtTextView":
            TextView createdAtTextView = convertView.findViewById(R.id.createdAtTextView);
            createdAtTextView.setText("Created at: " + formattedDate);
        } else {
            // Handle the case where createdAt is null
        }

        taskTextView.setText(task.getName());
        descriptionTextView.setText(task.getDescription()); // Set description
        categoryTextView.setText(task.getCategory()); // Set category
        taskCheckBox.setChecked(task.isCompleted());
        // Set the icon resource for the task
        iconImageView.setImageResource(task.getIconResourceId());

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

        // Set listeners for checkbox and edit button
        taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            notifyDataSetChanged();
        });

        editButton.setOnClickListener(v -> {
            // Handle edit button click
            if (editTaskListener != null) {
                editTaskListener.onEditTask(position);
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


