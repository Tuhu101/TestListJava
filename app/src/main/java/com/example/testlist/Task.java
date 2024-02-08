package com.example.testlist;

import java.util.Date;
public class Task {
    private String name;
    private String description;
    private String category;
    private int iconResourceId;
    private boolean completed;
    private Date createdAt;


    public Task(String name, String description, String category, int task_icon) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.iconResourceId = iconResourceId;
        this.completed = false; // Default to not completed
        this.createdAt = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and setter for icon resource ID
    public int getIconResourceId() {
        return R.drawable.task_icon;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    /*
     * This part is for the Time created.
     */
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}