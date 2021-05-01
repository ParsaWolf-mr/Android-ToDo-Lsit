package com.example.assignment02todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;


public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText taskDescription, title;
    private Button saveButton, deleteButton;
    private TextView tvDate, dateTextEdit;
    private static DataBankHandler db;
    private String currentDateString;
    private TaskClass taskClass;

    public void setDB(DataBankHandler db){
        this.db = db;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();


        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        taskDescription = findViewById(R.id.taslDescription);
        tvDate = findViewById(R.id.tv_date);
        dateTextEdit = findViewById(R.id.tv_date);
        title = findViewById(R.id.newTaskTitle);
        saveButton = findViewById(R.id.speicherButton);
        deleteButton = findViewById(R.id.deleteButton);

        // getting the id of card View
        //String titleStr = getIntent().getStringExtra("title");
        //String descriptionStr = getIntent().getStringExtra("task");
        int idstr= getIntent().getIntExtra("id", 0);
        //String dateStr = getIntent().getStringExtra("date");
        taskClass = new TaskClass();
        taskClass = db.getOneTask(idstr);

        taskDescription.setText(taskClass.getTaskDescription());
        title.setText(taskClass.getTaskTitle());
        dateTextEdit.setText(taskClass.getDate());

        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Date Clickable
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateBankFragment1();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(AddNewTask.this, MainActivity.class);
            @Override
            public void onClick(View v) {
                TaskClass newTask = null;
                try{
                    newTask = new TaskClass(title.getText().toString() , taskDescription.getText().toString(),false, "");
                }catch (Exception e){
                    // if failed the print an Error
                }
                db = new DataBankHandler(AddNewTask.this);
                boolean success = db.addOnDataBase(newTask);
                Toast.makeText(AddNewTask.this, "Success " + success , Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {    // need to fix
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewTask.this, MainActivity.class);
                db = new DataBankHandler(AddNewTask.this);
                // ToDo
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
                saveButton.setEnabled(title.getText().length()>0);
                deleteButton.setEnabled(title.getText().length()>0);
            }
        };
        title.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }
}