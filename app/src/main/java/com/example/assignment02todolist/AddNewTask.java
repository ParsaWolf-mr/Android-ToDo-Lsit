package com.example.assignment02todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText taskDescription, title;
    private Button saveButton, deleteButton;
    private TextView tvDate, dateTextView, tv_time;
    private static DataBankHandler db;
    private String currentDateString;
    private TaskClass taskClass;
    private ToDoHandler todoHandler;
    int t1Hour, t1Minute, t2Hour, t2Minute;

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

        taskDescription = findViewById(R.id.task_description_et);
        tvDate = findViewById(R.id.tv_date);
        dateTextView = findViewById(R.id.date_shower_tv);
        title = findViewById(R.id.task_title_et);
        tv_time = findViewById(R.id.time_tv);
        saveButton = findViewById(R.id.speicherButton);
        deleteButton = findViewById(R.id.deleteButton);

        // getting the id of card View
        int idstr= getIntent().getIntExtra("id", 0);
        int positionStr = getIntent().getIntExtra("position", 0);
        boolean update = getIntent().getBooleanExtra("update", false);

        taskClass = new TaskClass();
        taskClass = db.getOneTask(idstr);
        taskDescription.setText(taskClass.getTaskDescription());
        title.setText(taskClass.getTaskTitle());
        dateTextView.setText(taskClass.getDate());
        tv_time.setText(taskClass.getTime());

        saveButton.setEnabled(false);

        // Date Clickable
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateBankFragment1();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        // Time Clickable
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddNewTask.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Hour = hourOfDay;
                                t2Minute = minute;

                                String time = t2Hour + ":" + t2Minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    ClipboardManager tvTimer2 = null;
                                    tv_time.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t2Hour, t2Minute);
                timePickerDialog.show();
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            Intent intent = new Intent(AddNewTask.this, MainActivity.class);
            @Override
            public void onClick(View v) {

                boolean success;
                try{
                    if(update){
                           db.updateTask(taskClass.getId(), title.getText().toString(), taskDescription.getText().toString(), dateTextView.getText().toString(), tv_time.getText().toString());
                    }else {
                        taskClass = new TaskClass(title.getText().toString(), taskDescription.getText().toString(), false, dateTextView.getText().toString(), tv_time.getText().toString());
                        success = db.addOnDataBase(taskClass);
                        Toast.makeText(AddNewTask.this, "Success " + success , Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    // if failed the print an Error
                }

                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {    // need to fix
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddNewTask.this);
                alertDialogBuilder.setTitle("Confirm Exit ..!!");
                alertDialogBuilder.setMessage("Are you sure u want to exit");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteTask(taskClass.getId());
                        startActivity(new Intent(AddNewTask.this, MainActivity.class));
                        Toast.makeText(AddNewTask.this, "delete succesfull" , Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddNewTask.this, "you clicked on cancel", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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
                saveButton.setEnabled(taskDescription.getText().length()>0);
            }
        };
        title.addTextChangedListener(textWatcher);
        taskDescription.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        dateTextView.setText(currentDateString);
    }
}