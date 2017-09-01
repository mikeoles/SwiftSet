package com.michaeloles.swiftset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oles on 8/30/2017.
 */
public class WorkoutDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workouts.db";
    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORKOUTNAME = "name";
    public static final String COLUMN_EXERCISENAMES = "exercises";

    public WorkoutDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_WORKOUTS + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_WORKOUTNAME + " TEXT," +
                COLUMN_EXERCISENAMES + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

    //Add a new workout to the database
    public void addWorkout(Workout workout){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUTNAME,workout.getName());
        values.put(COLUMN_EXERCISENAMES,workout.exerciseNamesToString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WORKOUTS, null, values);
        db.close();
    }

    //Removes a workout with the name workoutName from the database
    public void deleteWorkout(String workoutName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WORKOUTS + " WHERE " + COLUMN_WORKOUTNAME + "='" + workoutName + "';");
        db.close();
    }

    //Returns a list of the users workouts that are stored in the database
    public ArrayList<Workout> getWorkouts(){
        ArrayList<Workout> workoutList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_WORKOUTNAME))!=null){
                String workoutName = c.getString(c.getColumnIndex(COLUMN_WORKOUTNAME));
                String exerciseNames = c.getString(c.getColumnIndex(COLUMN_EXERCISENAMES));
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(exerciseNames.split(",")));
                workoutList.add(new Workout(workoutName, list));
            }
            c.moveToNext();
        }
        db.close();
        return workoutList;
    }

    //Returns the number of rows in the exercise table
    public int numWorkouts(){
        String countQuery = "SELECT  * FROM " + TABLE_WORKOUTS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
