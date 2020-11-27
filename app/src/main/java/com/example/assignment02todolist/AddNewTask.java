package com.example.assignment02todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddNewTask extends MainActivity{

    private EditText newTaskText;
    private Button saveButton, deleteButton;
    static int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newTaskText = findViewById(R.id.newTask);
        saveButton = findViewById(R.id.speicherButton);
        deleteButton = findViewById(R.id.deleteButton);

        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);

        saveButton.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(AddNewTask.this, MainActivity.class);
            @Override
            public void onClick(View v) {
                TaskClass newTask = null;
                try{
                    newTask = new TaskClass( newTaskText.getText().toString(),false);
                }catch (Exception e){
                    // if failed the print an Error
                }
                DataBankHandler dataBankHandler = new DataBankHandler(AddNewTask.this);
                boolean success = dataBankHandler.addOnDataBase(newTask);
                Toast.makeText(AddNewTask.this, "Success " + success , Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {    // need to fix
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewTask.this, MainActivity.class);

                startActivity(intent);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                number++;
                saveButton.setEnabled(newTaskText.getText().length()>0);
                deleteButton.setEnabled(newTaskText.getText().length()>0);
            }
        };
        newTaskText.addTextChangedListener(textWatcher);
    }
}