package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Oles on 6/9/2017.
 */
public class ExerciseDb extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "exDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String EXERCISE_NAME_COL = "Name";

    public ExerciseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<String> getColumnsList(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"0 _id", "FullName"};
        String sqlTables = "exercises";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, null, null, null,
                null, null, null);

        ArrayList<String> columns = new ArrayList<>();
        c.moveToFirst();
        while (c.moveToNext()) {
            columns.add(c.getString(c.getColumnIndex(EXERCISE_NAME_COL)));
        }

        return columns;
    }
}
