package com.example.assignment02todolist;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView listOfTasks;
    public DataBankHandler db;
    private ToDoHandler taskHandler;
    private List<TaskClass> tasksList;
    private FloatingActionButton fmb;
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        tasksList = new ArrayList<>();

        // setting the Actionbar Color
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        db = new DataBankHandler(MainActivity.this);

        createNotificationChangel();

        // Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);

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

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tasksList = db.getEveryTask();
        /*
        for(TaskClass item: tasksList){
            try {
                notifyConfiguration(item.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } */
        taskHandler.setTask(tasksList);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Confirm Exit ..!!");
                alertDialogBuilder.setMessage("Are you sure u want to exit");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(1);
                    }

                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "you clicked on cancel", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.search:
                try {
                    notifyConfiguration(" ");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

        }
        return  false;
    }


    public void createNotificationChangel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "EasyDoChannel";
            String description = "Channel for EasyDo Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyEasyDo", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void notifyConfiguration(String dateStr) throws ParseException {
        String tempDateStr = dateStr;
        // creating simple data format
        //ToDO
        //SimpleDateFormat sdf= new SimpleDateFormat("hh:mm:ss");
        //Date date = sdf.parse(tempDateStr);

        Toast.makeText(this, "Reminder set!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
        PendingIntent pendingIntent =PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        //long notifyTime= date.getTime();
        long currentTime = System.currentTimeMillis() + 1000*10;
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent);

    }
}