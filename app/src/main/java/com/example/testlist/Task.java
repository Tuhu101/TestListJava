package com.example.testlist;

public class Task {
    private String name;
    private boolean completed;
    private String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.completed = false; // Default to not completed
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
