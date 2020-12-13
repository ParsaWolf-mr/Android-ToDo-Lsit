package com.example.assignment02todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBankHandler  extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String TODO_TABLE = "TODO_TABLE";
    private static final String ID = "ID";
    private static final String TASK = "TASK";
    private static final String STATUS = "STATUS";

    public DataBankHandler(@Nullable Context context) {
        super(context, TODO_TABLE, null, 1);
    }


    @Override  // create Table and execute the query
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + TODO_TABLE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, " + STATUS + " INTEGER)";
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE );
        onCreate(db);   //  update the old table
    }


    public boolean addOnDataBase(TaskClass newTask){
        getWriteAbel();
        ContentValues row = new ContentValues();

        row.put(TASK , newTask.getTask());
        row.put(STATUS, 0);
        long insert = db.insert(TODO_TABLE, null,row);
        if (insert == 1){
            return false;
        }else {
            return true;
        }
    }
    public void getWriteAbel(){
        db = this.getWritableDatabase();
    }

    public void getReadAble(){
        db = this.getReadableDatabase();
    }

    public void updateStauts(int id, boolean status){
        getWriteAbel();;

        ContentValues row = new ContentValues();
        row.put(STATUS, status);
        /*String updateQuery = "UPDATE TABLE " + TODO_TABLE + " SET "
                + STATUS + " = " + " true " + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(updateQuery, null);  */
        db.update(TODO_TABLE, row, ID + "=?", new String[]{String.valueOf(id)});
    }

    public boolean deleteTask (int id ){
        getWriteAbel();
        String query = "DELETE FROM " + TODO_TABLE + " WHERE "+ ID+ " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    @SuppressLint("Recycle")
    public void deleteCheckedTasks(){
        getWriteAbel();
        String query = "DELETE FROM " + TODO_TABLE +" WHERE " + STATUS + " IS " + 1 ;
        db.rawQuery(query, null);
    }

    public List<TaskClass> getEveryTask(){
        deleteCheckedTasks();
        getReadAble();
        List<TaskClass> listOfTasks = new ArrayList<>();

        String query = "SELECT * FROM " +TODO_TABLE +
                " ORDER BY "+ STATUS + " DESC ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                TaskClass task = new TaskClass();
                task.setId(cursor.getInt(0));
                task.setTask(cursor.getString(1));
                task.setStatus(cursor.getInt(2)==1? true: false);

                listOfTasks.add(task);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return listOfTasks;
    }

    public void updateTask(int id, String task){
        getWriteAbel();

        ContentValues row = new ContentValues();
        row.put(TASK, task );
        db.update(TODO_TABLE, row, ID+ "=?", new String[] {String.valueOf(id)});
    }
}