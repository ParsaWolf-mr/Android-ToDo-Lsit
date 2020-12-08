package com.example.assignment02todolist;

public class TaskClass {

    private String task;
    private int id;
    private boolean status;

    public TaskClass(String task, boolean status) {
        this.task = task;
        this.status = status;
    }

    public TaskClass() {
    }

    public boolean getStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}

    public String getTask() {
        return task;
    }

    public int getId() {return id;}

    public void setTask(String task) {
        this.task = task;
    }

    public void setId(int id) {
        this.id = id;
    }
}
