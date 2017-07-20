package com.michaeloles.swiftset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ExerciseDb extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "main_exercises_2.db";
    private static final String EXERCISE_TABLE = "exercises";
    private static final int DATABASE_VERSION = 1;
    private static final String EXERCISE_NAME_COL = "Name";
    private static final String URL_COL = "Url";
    private static final HashMap<String,String> urls = new HashMap<>();

    public ExerciseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Returns a list of all the columns remaining in the database
    //Also fills a matching arrayList of the urls of all the exercises
    public ArrayList<String> getColumnsList(){
        SQLiteDatabase db = getReadableDatabase();
        String where = "[Eliminated] == '0'";
        String[] tableColumns = {EXERCISE_NAME_COL,URL_COL};
        Cursor c = db.query(EXERCISE_TABLE, tableColumns, where, null,
                null, null, null);

        ArrayList<String> columns = new ArrayList<>();
        c.moveToFirst();
        try {
            do {
                String colName = c.getString(c.getColumnIndex(EXERCISE_NAME_COL));
                columns.add(colName);
                String url = c.getString(c.getColumnIndex(URL_COL));
                urls.put(colName,url);
            } while (c.moveToNext());
        }catch (CursorIndexOutOfBoundsException ae){
            Log.e("Cursor Error", ae.toString());
        }
        db.close();
        c.close();
        return columns;
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
        String where;
        if(dbSortCategory.equals("Equipment")){
            where = "[" + dbSortCategory + "] NOT LIKE '%" + sortByList[0] + "%'";
        }else {
            where = "[" + dbSortCategory + "] != '" + sortByList[0] + "'";
            for (int i = 1; i < sortByList.length; i++) {
                where += " AND [" + dbSortCategory + "] != '" + sortByList[i] + "'";
            }
            where += " OR [" + dbSortCategory + "] is null";
        }
        Log.v("olesy",where);
        ContentValues cv = new ContentValues();
        cv.put("Eliminated","1");
        db.update(EXERCISE_TABLE,cv,where,null);
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
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //Getters and Setters
    public HashMap<String,String> getUrls() {
        return urls;
    }
}
