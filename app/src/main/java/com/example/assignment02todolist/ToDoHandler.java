package com.example.assignment02todolist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ToDoHandler extends RecyclerView.Adapter<ToDoHandler.MyViewHolder> {

    private  MainActivity mainActivity;
    private List<TaskClass> todoList;
    private DataBankHandler db;

    public ToDoHandler(DataBankHandler db, MainActivity activity){
        this.mainActivity = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        db.getWriteAbel();
        TaskClass item = todoList.get(position);
        holder.taskText.setText(item.getTask());
        holder.task.setChecked(item.getStatus());

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStauts(item.getId(), true);
                }else{
                    db.updateStauts(item.getId(), false);
                }
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        TextView taskText;
        MyViewHolder(View view){
            super(view);

            // Setting the on Click listener here
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // creating an Intent
                    Intent intent = new Intent(v.getContext(), AddNewTask.class);
                    intent.putExtra("id", v.getId());
                     v.getContext().startActivity(intent);
                }
            });


            task = view.findViewById(R.id.checkbox);
            taskText = view.findViewById(R.id.task_layout_text);
        }
    }

    public int getItemCount(){ return todoList.size();}

    public void setTask(List<TaskClass> todoList){
        this.todoList = todoList;
        Collections.reverse(todoList);
        notifyDataSetChanged();
    }
}
