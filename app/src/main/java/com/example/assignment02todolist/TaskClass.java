package com.example.assignment02todolist;

public class TaskClass {

    private String taskDescription, taskTitle ;
    private int id;
    private boolean status;
    private String date;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public TaskClass(String taskTitle, String taskDescription, boolean status, String date, String time) {
        this.taskTitle= taskTitle;
        this.taskDescription = taskDescription;
        this.date = date;
        this.time = time;
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
