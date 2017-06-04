package edu.pitt.cs.cs1635.mbo10.swiftset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Oles on 6/3/2017.
 */
public class ExerciseDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exercise.db";
    public static final String TABLE_NAME = "exercise";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRIMARY = "primary";
    public static final String COL_BODYWEIGHT = "bodyweight";
    public static final String COL_BARBELL = "barbell";
    public static final String COL_SPORT = "sport";

    public ExerciseDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COL_NAME + " TEXT " +
                 ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add row to database
    public void addExercise(Exercise exercise){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, exercise.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Delete an exercise from the database
    public void deleteExercise(String exerciseName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COL_NAME + "=\"" + exerciseName + "\";");
    }

    //Print out the database
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + "WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query,null);
        //Move to the first row in your rewrite
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("name"))!=null){
                dbString += c.getString(c.getColumnIndex("name"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

}
