package com.example.to_do_list.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorSpace;

import com.example.to_do_list.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";

    private static final String COL_1 = "ID";
    private static final String COL_2 = "Task";
    private static final String COL_3 = "Status";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " INTEGER)";
        db.execSQL(createTable); // Correct method and correct variable used
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    private void insertTask(ToDoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.getTask());
        contentValues.put(COL_3,0);

        db.insert(TABLE_NAME, null,contentValues);

    }

    public void updateTask (int ID, String Task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, Task);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(ID)});
    }

    public void updateStatus(int ID, int Status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,Status);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(ID)});

    }

    public void deleteTask(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(ID)});
    }
    public List<ToDoModel> getAllTask(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if (cursor!= null){
                if (cursor.moveToFirst()){
                    do{
                        ToDoModel toDoModel= new ToDoModel();

                        toDoModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        toDoModel.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        toDoModel.setStatus(cursor.getString(cursor.getColumnIndex(COL_3)));
                        modelList.add(toDoModel);

                    }while (cursor.moveToNext());
                }
            }
        }finally{
           db.endTransaction();
           cursor.close();
        }
        return modelList;
    }
}
