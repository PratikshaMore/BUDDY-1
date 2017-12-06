package com.cornez.todotodayii;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    //TASK 1: DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pet_Data1";
    private static final String DATABASE_TABLE = "Pet_Item1";


    //TASK 2: DEFINE THE COLUMN NAMES FOR THE TABLE
    private static final String KEY_TASK_ID = "_id";
    private static final String KEY_BREED = "breed";
    private static final String KEY_NAME = "name";
    private static final String KEY_OWNER_NAME = "ownerName";
    private static final String KEY_HISTORY = "history";
    private static final String KEY_AGE = "age";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_IS_DONE = "is_done";

    private int taskCount;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_TASK_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT, " + KEY_BREED + " TEXT, " + KEY_AGE + " INTEGER, "
                + KEY_WEIGHT + " INTEGER," + KEY_OWNER_NAME + " TEXT, " +KEY_CONTACT+" STRING, "+ KEY_HISTORY + " TEXT, "+ KEY_IS_DONE + " INTEGER)";

        db.execSQL(table);
    }

    public void resetThis(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS pet_Items");
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // DROP OLDER TABLE IF EXISTS
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        // CREATE TABLE AGAIN
        onCreate(database);
    }


    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE
    // Adding new task
    public void addToDoItem(Pet_Details task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        // values.put(KEY_TASK_ID,task.get_id());
        values.put(KEY_NAME, task.getName()); // pet name
        values.put(KEY_BREED, task.getBreed());
        values.put(KEY_AGE, task.getAge());
        values.put(KEY_WEIGHT, task.getWeight());
        values.put(KEY_OWNER_NAME, task.getOwnerName());
        values.put(KEY_CONTACT,task.getContact());
        values.put(KEY_HISTORY, task.getHistory());

        values.put(KEY_IS_DONE, task.getIs_done());

        // INSERT THE ROW IN THE TABLE
        db.insert(DATABASE_TABLE, null, values);
        taskCount++;

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public List<Pet_Details> getAllTasks() {

        //GET ALL THE TASK ITEMS ON THE LIST
        List<Pet_Details> todoList = new ArrayList<Pet_Details>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE TODO TASKS
        if (cursor.moveToFirst()) {
            do {
                Pet_Details task = new Pet_Details();
                task.set_id(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setBreed(cursor.getString(2));
                task.setAge(cursor.getInt(3));
                task.setWeight(cursor.getInt(4));
                task.setOwnerName(cursor.getString(5));
                task.setContact(cursor.getString(6));
                task.setHistory(cursor.getString(7));
                task.setIs_done(cursor.getInt(8));
                todoList.add(task);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TASKS FROM THE TABLE
        return todoList;
    }

    public void clearAll(List<Pet_Details> list) {
        //GET ALL THE LIST TASK ITEMS AND CLEAR THEM
        list.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, new String[]{});
        db.close();
    }

    public void deleteSelected(List<Pet_Details> list) {

        for(Iterator<Pet_Details> i=list.iterator() ; i.hasNext();){
            Pet_Details item=i.next();
            if(item.getIs_done()==1) i.remove();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_IS_DONE+"=1", new String[]{});
        db.close();
    }

    public void updateTask(Pet_Details task) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName()); // pet name
        values.put(KEY_BREED, task.getBreed());
        values.put(KEY_AGE, task.getAge());
        values.put(KEY_WEIGHT, task.getWeight());
        values.put(KEY_OWNER_NAME, task.getOwnerName());
        values.put(KEY_CONTACT,task.getContact());
        values.put(KEY_HISTORY, task.getHistory());
        values.put(KEY_IS_DONE, task.getIs_done());
        db.update(DATABASE_TABLE, values, KEY_TASK_ID + " = ?", new String[]{String.valueOf(task.get_id())});
        db.close();
    }

}
