package com.example.assignment02todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoHandler extends RecyclerView.Adapter<ToDoHandler.ViewHolder> {

    private MainActivity mainActivity;
    private List<TaskClass> todoList;

    public ToDoHandler(MainActivity activity){
        this.mainActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHandler.ViewHolder holder, int position) {
        TaskClass item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.getStatus());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.checkbox);
        }
    }

    public int getItemCount(){ return todoList.size();}

    public void setTask(List<TaskClass> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }
}
