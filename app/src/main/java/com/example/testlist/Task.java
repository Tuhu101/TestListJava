package com.example.testlist;

public class Task {
    private String name;
    private String description;
    private String category;
    private boolean completed;


    public Task(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
