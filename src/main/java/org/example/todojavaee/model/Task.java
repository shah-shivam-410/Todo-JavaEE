package org.example.todojavaee.model;

import java.time.LocalDateTime;

public class Task {

    private int id;
    private String title;
    private String description;
    private boolean  completed;
    private LocalDateTime completedAt;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Task(int id, String title, String description, boolean completed, LocalDateTime completedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", completedAt=" + completedAt +
                '}';
    }

}
