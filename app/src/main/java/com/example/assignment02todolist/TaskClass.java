package com.example.assignment02todolist;

public class TaskClass {

    private String taskDescription, taskTitle ;
    private int id;
    private boolean status;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public TaskClass(String taskTitle, String taskDescription, boolean status) {
        this.taskTitle= taskTitle;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public TaskClass() {
    }

    public boolean getStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getId() {return id;}

    public void setTaskDescription(String task) {
        this.taskDescription = task;
    }

    public void setId(int id) {
        this.id = id;
    }
}
