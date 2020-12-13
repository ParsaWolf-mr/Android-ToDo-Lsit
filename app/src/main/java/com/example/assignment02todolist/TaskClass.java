package com.example.assignment02todolist;

public class TaskClass {

    private String taskDescription, taskTitle ;
    private int id;
    private boolean status;

    public TaskClass(String taskTitle, String taskDescription, boolean status) {
        this.taskTitle= taskTitle;
        this.taskDescription = taskDescription;
        this.status = status;
    }

    public TaskClass() {
    }

    public boolean getStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

    public String getTask() {
        return taskDescription;
    }

    public int getId() {return id;}

    public void setTask(String task) {
        this.taskDescription = task;
    }

    public void setId(int id) {
        this.id = id;
    }
}
