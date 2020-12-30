package com.example.assignment02todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView /*tvTimer1,*/ tvTimer2;
    int t1Hour, t1Minute, t2Hour, t2Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();

        Button button =  findViewById(R.id.button);

        // setting the Actionbar Color
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DateBankFragment1();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //tvTimer1 = findViewById(R.id.tv_timer1);
        tvTimer2 = findViewById(R.id.tv_timer2);

        tvTimer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Activity2.this,
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
                                    tvTimer2.setText(f12Hours.format(date));
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

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(currentDateString);
    }

}