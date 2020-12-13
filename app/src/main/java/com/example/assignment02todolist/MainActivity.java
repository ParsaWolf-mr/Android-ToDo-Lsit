package com.example.assignment02todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listOfTasks;
    public DataBankHandler db;
    private ToDoHandler taskHandler;
    private List<TaskClass> tasksList;
    private FloatingActionButton fmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = new ArrayList<>();

        db = new DataBankHandler(MainActivity.this);


        listOfTasks = findViewById(R.id.listOfTasks);
        listOfTasks.setLayoutManager(new LinearLayoutManager(this));
        taskHandler = new ToDoHandler(db, this);
        listOfTasks.setAdapter(taskHandler);
        fmb =(FloatingActionButton)  findViewById(R.id.newTask_fabbtn);



        fmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTask.class);
                startActivity(intent);

            }
        });
        tasksList = db.getEveryTask();
        taskHandler.setTask(tasksList);

    }
}