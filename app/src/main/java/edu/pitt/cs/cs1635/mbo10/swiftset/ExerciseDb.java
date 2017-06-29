package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Oles on 6/9/2017.
 */
public class ExerciseDb extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "exDatabaseOne.db";
    private static final String EXERCISE_TABLE = "exercises";
    private static final int DATABASE_VERSION = 1;
    private static final String EXERCISE_NAME_COL = "Name";

    public ExerciseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<String> getColumnsList(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"0 _id", "FullName"};

        qb.setTables(EXERCISE_TABLE);
        Cursor c = qb.query(db, null, null, null,
                null, null, null);

        ArrayList<String> columns = new ArrayList<>();
        c.moveToFirst();
        try {
            do {
                String colName = c.getString(c.getColumnIndex(EXERCISE_NAME_COL));
                columns.add(colName);
            } while (c.moveToNext());
        }catch (ArrayIndexOutOfBoundsException ae){
        }

        return columns;
    }

    //Removes all of the rows where the column dbSortCategory does not contain dbSortBy
    public void removeRows(String dbSortBy, String dbSortCategory) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EXERCISE_TABLE,dbSortCategory + " = " + dbSortBy,null);
        db.close();
    }
}
