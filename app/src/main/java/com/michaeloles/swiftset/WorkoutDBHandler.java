package com.michaeloles.swiftset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by Oles on 8/30/2017.
 */
public class WorkoutDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "workouts.db";
    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WORKOUTNAME = "name";
    public static final String COLUMN_EXERCISENAMES = "exercises";
    public static final String COLUMN_TEMPLATE = "template";

    private static final String DATABASE_ALTER_ADD_DATE = "ALTER TABLE "
            + TABLE_WORKOUTS + " ADD COLUMN " + COLUMN_DATE
            + " TEXT DEFAULT '" + Calendar.getInstance().getTime().toString()+"'";

    private static final String DATABASE_ALTER_ADD_TEMPLATE = "ALTER TABLE "
            + TABLE_WORKOUTS + " ADD COLUMN " + COLUMN_TEMPLATE
            + " INTEGER DEFAULT '1'";

    public WorkoutDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_WORKOUTS + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_WORKOUTNAME + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_EXERCISENAMES + " TEXT," +
                COLUMN_TEMPLATE + " INTEGER" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<2) {//v1->v2 adds a date value to every workout
            db.execSQL(DATABASE_ALTER_ADD_DATE);
        }
        if(oldVersion<4){//Add isTemplate to every workout
            db.execSQL(DATABASE_ALTER_ADD_TEMPLATE);//No previous workouts will be templates
        }
    }

    //Add a new workout to the database
    public void addWorkout(Workout workout){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUTNAME,workout.getName());
        values.put(COLUMN_EXERCISENAMES,workout.exerciseNamesToString());
        Log.v("olesy",workout.exerciseNamesToString());
        if(workout.isTemplate()){
            values.put(COLUMN_TEMPLATE,1);
            values.put(COLUMN_DATE,Calendar.getInstance().getTime().toString());
        }else{
            values.put(COLUMN_DATE,workout.getDate().getTime().toString());
            values.put(COLUMN_TEMPLATE,0);
        }
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
                Integer isTemplate = c.getInt(c.getColumnIndex(COLUMN_TEMPLATE));
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Calendar workoutDate = Calendar.getInstance();
                try {
                    workoutDate.setTime(sdf.parse(c.getString(c.getColumnIndex(COLUMN_DATE))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(exerciseNames.split(",")));
                Workout w = new Workout(workoutName, workoutDate, list);
                w.setTemplate(isTemplate==1);
                workoutList.add(w);
            }
            c.moveToNext();
        }
        db.close();
        c.close();
        return workoutList;
    }

    //Returns the number of rows in the exercise table
    public int numWorkouts(){
        String countQuery = "SELECT  * FROM " + TABLE_WORKOUTS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        return count;
    }

    //Gets all the workouts in the database and returns all of the dates that have workouts on them (Calendar)
    public HashSet<CalendarDay> getDates(){
        HashSet<CalendarDay> dates = new HashSet<>();
        ArrayList<Workout> workouts = this.getWorkouts();
        for(Workout w:workouts){
            if(!w.isTemplate()) {//Only workouts that are not templates display dates
                Calendar cal = w.getDate();
                CalendarDay calDay = CalendarDay.from(cal);
                dates.add(calDay);
            }
        }
        return dates;
    }
}