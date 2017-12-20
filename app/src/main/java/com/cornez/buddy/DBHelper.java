package com.cornez.buddy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.cornez.buddy.Utils.getDateFromSQLDateTime;
import static java.lang.Math.toIntExact;


public class DBHelper extends SQLiteOpenHelper {
    //********** DEFINE THE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pet_Data";
    private static final String DATABASE_TABLE_PET = "Pets";
    private static final String DATABASE_TABLE_HISTORY = "Pet_History";


    //********** DEFINE THE COLUMN NAMES FOR THE TABLES
    private static final String KEY_PET_ID = "id";
    private static final String KEY_PET_BREED = "breed";
    private static final String KEY_PET_NAME = "name";
    private static final String KEY_PET_OWNER_NAME = "ownerName";
    private static final String KEY_PET_IMAGE_PATH = "imagePath";
    private static final String KEY_PET_CONTACT = "contact";

    private static final String KEY_HIST_ID = "id";
    private static final String KEY_HIST_PET_ID = "pid";
    private static final String KEY_HIST_AGE = "age";
    private static final String KEY_HIST_WEIGHT = "weight";
    private static final String KEY_HIST_DESC = "description";
    private static final String KEY_HIST_DATE = "created_at";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String table1 = "CREATE TABLE " + DATABASE_TABLE_PET + "("
                + KEY_PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_PET_NAME + " TEXT, "
                + KEY_PET_BREED + " TEXT, "
                + KEY_PET_OWNER_NAME + " TEXT, "
                + KEY_PET_CONTACT+" STRING, "
                + KEY_PET_IMAGE_PATH + " TEXT )";

        db.execSQL(table1);

        String table2 = "CREATE TABLE " + DATABASE_TABLE_HISTORY + "("
                + KEY_HIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_HIST_PET_ID + " INTEGER, "
                + KEY_HIST_AGE + " INTEGER, "
                + KEY_HIST_WEIGHT + " REAL, "
                + KEY_HIST_DESC + " TEXT, "
                + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP )";



        db.execSQL(table2);
    }

    public void resetThis(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PET);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_HISTORY);
        onCreate(db);
        Log.d("RESET_TAG", "Called reset DB");
    }

    public void seedDatabase() {
        int seedCount = 10;
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i =0 ;i < seedCount; i++){
            ContentValues values = new ContentValues();
            values.put(KEY_PET_NAME, "PetName"+i);
            values.put(KEY_PET_BREED, "PetBreed"+i);
            values.put(KEY_PET_OWNER_NAME, "PetOwnerName"+i);
            values.put(KEY_PET_CONTACT, "3238099061");
            values.put(KEY_PET_IMAGE_PATH, "PetImagePath"+i);
            long insertedId = db.insert(DATABASE_TABLE_PET, null, values);
            int insertedIdInt = toIntExact(insertedId);

            for(int j = 0; j < seedCount/2 ; j++){
                ContentValues hist_values = new ContentValues();
                hist_values.put(KEY_HIST_PET_ID, insertedIdInt);
                hist_values.put(KEY_HIST_AGE, i+j+1);
                hist_values.put(KEY_HIST_WEIGHT, (i+j+1) * 3);
                hist_values.put(KEY_HIST_DESC, "Random String of what is wrong with this pet"+String.valueOf(i)+" "+String.valueOf(j));
                db.insert(DATABASE_TABLE_HISTORY, null, hist_values);
            }

        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // DROP OLDER TABLE IF EXISTS
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PET);
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_HISTORY);

        // CREATE TABLE AGAIN
        onCreate(database);
    }


    //********** DATABASE OPERATIONS:  ADD, EDIT, DELETE

    public Pet addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE TASK DESCRIPTION
        // values.put(KEY_TASK_ID,task.get_id()); AUTOINCREMENT

        values.put(KEY_PET_NAME, pet.getName()); // pet name
        values.put(KEY_PET_BREED, pet.getBreed());
        values.put(KEY_PET_OWNER_NAME, pet.getOwnerName());
        values.put(KEY_PET_CONTACT,pet.getContact());
        values.put(KEY_PET_IMAGE_PATH, pet.getImagePath());



        // INSERT THE ROW IN THE TABLE
        long insertedId = db.insert(DATABASE_TABLE_PET, null, values);
        int insertedIdInt = toIntExact(insertedId);
        pet.setId(insertedIdInt);

        // CLOSE THE DATABASE CONNECTION
        db.close();
        return pet;
    }

    public Pet updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PET_NAME, pet.getName()); // pet name
        values.put(KEY_PET_BREED, pet.getBreed());
        values.put(KEY_PET_OWNER_NAME, pet.getOwnerName());
        values.put(KEY_PET_CONTACT,pet.getContact());
        values.put(KEY_PET_IMAGE_PATH, pet.getImagePath());
        db.update(DATABASE_TABLE_PET, values, KEY_PET_ID + " = ?", new String[]{String.valueOf(pet.getId())});
        db.close();
        return pet;
    }

    public History addPetHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE HISTORY DESCRIPTION
        //  values.put(KEY_HIST_ID, history.getId()); AUTOINCREMENT
        values.put(KEY_HIST_PET_ID, history.getPid());
        values.put(KEY_HIST_AGE, history.getAge());
        values.put(KEY_HIST_WEIGHT, history.getWeight());
        values.put(KEY_HIST_DESC, history.getDescription());

        // INSERT THE ROW IN THE TABLE
        long insertedId = db.insert(DATABASE_TABLE_HISTORY, null, values);
        int insertedIdInt = toIntExact(insertedId);
        history.setId(insertedIdInt);

        // CLOSE THE DATABASE CONNECTION
        db.close();

        return history;
    }

    public History updatePetHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HIST_ID, history.getId());
        values.put(KEY_HIST_PET_ID, history.getPid());
        values.put(KEY_HIST_AGE, history.getAge());
        values.put(KEY_HIST_WEIGHT, history.getWeight());
        values.put(KEY_HIST_DESC, history.getDescription());
        db.update(DATABASE_TABLE_HISTORY, values, KEY_HIST_ID + " = ?", new String[]{String.valueOf(history.getId())});
        db.close();

        return history;
    }


    public List<Pet> getAllPets() {

        //GET ALL THE TASK ITEMS ON THE LIST
        List<Pet> petList = new ArrayList<Pet>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_PET + " ORDER BY " + KEY_PET_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE TODO TASKS
        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet();
                pet.setId(cursor.getInt(0));
                pet.setName(cursor.getString(1));
                pet.setBreed(cursor.getString(2));
                pet.setOwnerName(cursor.getString(3));
                pet.setContact(cursor.getString(4));
                pet.setImagePath(cursor.getString(5));

                petList.add(pet);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TASKS FROM THE TABLE
        return petList ;
    }
    public List<History> getAllPetHistory(int petId) {

        //GET ALL THE TASK ITEMS ON THE LIST
        List<History> petHistoryList = new ArrayList<History>();

        //SELECT ALL QUERY FROM THE TABLE
        String selectQuery;
        if(petId == 0)
            selectQuery = "SELECT * FROM " + DATABASE_TABLE_HISTORY;
        else
            selectQuery = "SELECT * FROM " + DATABASE_TABLE_HISTORY + " WHERE " + KEY_HIST_PET_ID + "=" + String.valueOf(petId) + " ORDER BY "+ KEY_HIST_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // LOOP THROUGH THE TODO TASKS
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(cursor.getInt(0));
                history.setPid(cursor.getInt(1));
                history.setAge(cursor.getInt(2));
                history.setWeight(cursor.getInt(3));
                history.setDescription(cursor.getString(4));
                history.setVisitDate(getDateFromSQLDateTime(cursor.getString(5)));
                petHistoryList.add(history);
            } while (cursor.moveToNext());
        }

        // RETURN THE LIST OF TASKS FROM THE TABLE
        return petHistoryList;
    }

//    public void clearAll(List<Pet_Details> list) {
//        //GET ALL THE LIST TASK ITEMS AND CLEAR THEM
//        list.clear();
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(DATABASE_TABLE, null, new String[]{});
//        db.close();
//    }

    public void deleteSelectedPets(List<Pet> list) {
        if(list.isEmpty())
            return;

        SQLiteDatabase db = this.getWritableDatabase();

        for(Iterator<Pet> i=list.iterator() ; i.hasNext();){
            Pet item=i.next();
            db.delete(DATABASE_TABLE_PET, KEY_PET_ID+"="+item.getId(), new String[]{});
            i.remove();
        }
        db.close();
        return;
    }

    public void deleteSelectedHistory(List<History> list) {
        if(list.isEmpty())
            return;

        SQLiteDatabase db = this.getWritableDatabase();

        for(Iterator<History> i=list.iterator() ; i.hasNext();){
            History item=i.next();
            db.delete(DATABASE_TABLE_HISTORY, KEY_HIST_ID+"="+item.getId(), new String[]{});
            i.remove();
        }
        db.close();
        return;
    }


}
