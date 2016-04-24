package io.github.imtilab.dailymuslim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Touhidul_MTI on 23-Apr-16.
 */
//***Important for this database design***
//Here, I considered 1st row of the database for the high score and
//2nd row of the database for the first score made by the user.
//first row id is 1 and second row id is 2

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Muslim_Score_Database.db";
    private static final String TABLE_NAME = "ScoreBoard";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_DATE = "date";

    Context context;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;//need to make toast, optional
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryToCreateTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE + " TEXT, " + COLUMN_DATE + " TEXT)";

        db.execSQL(queryToCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //called when need to add score to database
    public long addScore(String newScore, String newDate) {
        SQLiteDatabase sql_db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, newScore);
        values.put(COLUMN_DATE, newDate);

        long longNumber = sql_db.insert(TABLE_NAME, null, values);

        sql_db.close();
        return longNumber;
    }
    //called when need single row value of the database to print or display
    //take a row id ant return a string array of size two
    //make single score info score and date, then return the array
    public String[] getSingleScore_FromDB_ToStringArray_By_ID(int id) {
        String[] scoreArray = new String[2];

        SQLiteDatabase sql_db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id + "";

        //cursor point to a location in result
        Cursor cursor = sql_db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int scoreColumnIndex = cursor.getColumnIndex(COLUMN_SCORE);
            int dateColumnIndex = cursor.getColumnIndex(COLUMN_DATE);
            //upper two line getting column index is not mandatory as I know that which column is what like
            //id 0, score 1, date 2. I can directly put 0,1,2 inside below line like: cursor.getString(1)..;

            scoreArray[0] = cursor.getString(scoreColumnIndex);
            scoreArray[1] = cursor.getString(dateColumnIndex);
        }
        sql_db.close();
        return scoreArray;
    }

    //called when user makes a new high score. OR may be try to reset the score board
    //row 1 (id 1) is for high score and row 2 (id 2) is for first score made by the user
    //take score, date and row id to update data
    public void updateScore(String newHighScore, String highScoreDate, int rowId) {
        SQLiteDatabase sql_db = getWritableDatabase();

        sql_db.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_SCORE + " = '" + newHighScore + "', "+
                COLUMN_DATE+" = '"+highScoreDate+"' WHERE " + COLUMN_ID + " = "+rowId);
    }
    //called when need to know if this is first score to insert or second new high score.
    //Or two times inserted already, now need to update highScore
    //return row number
    public int getNumberOfInsertedRow(){
        SQLiteDatabase sqldb = getWritableDatabase();
        String query = "SELECT "+COLUMN_ID+" FROM "+TABLE_NAME;

        Cursor cursor = sqldb.rawQuery(query, null);

        return cursor.getCount();
    }
}