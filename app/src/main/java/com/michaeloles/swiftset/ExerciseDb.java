package com.michaeloles.swiftset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseDb extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "main_exercises_13.db";
    private static final String EXERCISE_TABLE = "exercises";
    private static final int DATABASE_VERSION = 1;
    private static final String EXERCISE_ID_COL = "_id";
    private static final String EXERCISE_NAME_COL = "Name";
    private static final String URL_COL = "Url";
    private static final HashMap<String,String> urls = new HashMap<>();//Map exercise ID to exercise URL
    private static final HashMap<String,String> names = new HashMap<>();//Map exercise ID to exercise Name

    public ExerciseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Returns a list of all the columns remaining in the database
    //Also fills a matching arrayList of the urls of all the exercises
    public ArrayList<String> getColumnsList(){
        SQLiteDatabase db = getReadableDatabase();
        String where = "[Eliminated] == '0'";
        String[] tableColumns = {EXERCISE_ID_COL,URL_COL,EXERCISE_NAME_COL};
        Cursor c = db.query(EXERCISE_TABLE, tableColumns, where, null,
                null, null, null);

        ArrayList<String> columns = new ArrayList<>();
        c.moveToFirst();
        try {
            do {
                String colID = c.getString(c.getColumnIndex(EXERCISE_ID_COL));
                columns.add(colID);
                String url = c.getString(c.getColumnIndex(URL_COL));
                urls.put(colID,url);
                String name = c.getString(c.getColumnIndex(EXERCISE_NAME_COL));
                names.put(colID,name);
            } while (c.moveToNext());
        }catch (CursorIndexOutOfBoundsException ae){
            Log.e("Cursor Error", ae.toString());
        }
        db.close();
        c.close();
        return columns;
    }

    //Removes a row if it contains any of the equipment the user has hidden
    public void EquipRemoveRows(ArrayList<String> toRemove) {
        if(toRemove==null || toRemove.size()<1) return;
        SQLiteDatabase db = getWritableDatabase();
        String where = "([Equipment] LIKE '%" + toRemove.get(0) + "%'";
        for (int i = 1; i < toRemove.size(); i++) {
            where += " OR [EQUIPMENT] LIKE '%" + toRemove.get(i) + "%'";
        }
        where += ") OR [EQUIPMENT] is null";
        ContentValues cv = new ContentValues();
        cv.put("Eliminated", "1");
        db.update(EXERCISE_TABLE, cv, where, null);
        db.close();
    }

    public void removeDifficultyAbove(String level) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "([DIFFICULTY] > " + level + ") OR [DIFFICULTY] is null";
        ContentValues cv = new ContentValues();
        cv.put("Eliminated", "1");
        db.update(EXERCISE_TABLE, cv, where, null);
        db.close();
    }

    //Removes all of the rows where the column dbSortCategory does not contain dbSortBy
    public void removeRows(String dbSortBy, String dbSortCategory) {
        SQLiteDatabase db = getWritableDatabase();
        String[] sortByList;
        if(dbSortBy.contains("/")) {
            sortByList = dbSortBy.split("/");
        }else{
            sortByList = new String[]{dbSortBy};
        }

        String where, sqlSearchKey;

        if(dbSortCategory.equals("Equipment")){
            sqlSearchKey = "NOT LIKE";
        }else {
            sqlSearchKey = "!=";
        }

        where = "([" + dbSortCategory + "] " + sqlSearchKey + " '" + sortByList[0] + "'";
        for (int i = 1; i < sortByList.length; i++) {
            where += " AND [" + dbSortCategory + "] " + sqlSearchKey + " '" + sortByList[i] + "'";
        }
        where += ") OR [" + dbSortCategory + "] is null";
        ContentValues cv = new ContentValues();
        cv.put("Eliminated", "1");
        db.update(EXERCISE_TABLE, cv, where, null);
        db.close();
    }

    //At the start of the program makes sure all possible exercises are available to the user
    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Eliminated","0");
        db.update(EXERCISE_TABLE,cv,null,null);
        db.close();
    }

    //Returns the number of rows in the exercise table
    public int numRows(){
        String countQuery = "SELECT  * FROM " + EXERCISE_TABLE + " WHERE [Eliminated] == '0'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Given an exercise name, return the url of that name
    //If name not found return an empty string
    public String getUrlByExerciseName(String name){
        String url = "";
        SQLiteDatabase db = getReadableDatabase();
        String where = "[" + EXERCISE_NAME_COL + "] == ?";
        String[] tableColumns = {EXERCISE_NAME_COL,URL_COL};
        //Third arg allows strings with quotes to work and mitigates SQL injection
        Cursor c = db.query(EXERCISE_TABLE, tableColumns, where, new String[] { name },
                null, null, null);
        int count = c.getCount();
        if(count==1) {
            c.moveToFirst();
            try {
                url = c.getString(c.getColumnIndex(URL_COL));
            } catch (CursorIndexOutOfBoundsException ae) {
                Log.e("Cursor Error", ae.toString());
            }
        }
        db.close();
        c.close();
        return url;
    }

    public String getUrlFromExerciseId(int id){
        String url = "";
        String idString = Integer.toString(id);
        SQLiteDatabase db = getReadableDatabase();
        String where = "[" + EXERCISE_ID_COL + "] == ?";
        String[] tableColumns = {URL_COL};
        //Third arg allows strings with quotes to work and mitigates SQL injection
        Cursor c = db.query(EXERCISE_TABLE, tableColumns, where, new String[] { idString },
                null, null, null);
        int count = c.getCount();
        if(count==1) {
            c.moveToFirst();
            try {
                url = c.getString(c.getColumnIndex(URL_COL));
            } catch (CursorIndexOutOfBoundsException ae) {
                Log.e("Cursor Error", ae.toString());
            }
        }else{
            Log.e("Url Error","Cant find url for exercise with ID: " + id);
        }
        db.close();
        c.close();
        return url;
    }

    public static String getNameFromExerciseId(String id){
        return names.get(id);
    }

    //Getters and Setters
    public HashMap<String,String> getUrls() {
        return urls;
    }
    public static HashMap<String,String> getNames() { return names; }
}
