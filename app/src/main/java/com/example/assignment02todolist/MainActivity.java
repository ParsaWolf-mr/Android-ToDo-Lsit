package com.example.assignment02todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listOfTasks;
    public DataBankHandler db;
    private ToDoHandler taskHandler;
    private List<TaskClass> tasksList;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("EasyDo");

        tasksList = new ArrayList<>();

        db = new DataBankHandler(MainActivity.this);


        listOfTasks = findViewById(R.id.listOfTasks);
        listOfTasks.setLayoutManager(new LinearLayoutManager(this));
        taskHandler = new ToDoHandler(db, this);
        listOfTasks.setAdapter(taskHandler);
        floatingActionButton = findViewById(R.id.floatingActionButton);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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