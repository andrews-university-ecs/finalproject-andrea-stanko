package edu.andrews.cptr252.andreastanko.finalproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbSchema.QuizTable;

public class quizDbHelper extends SQLiteOpenHelper {
    /** Current DB version. Increment as DB structure changes */
    private static final int VERSION = 1;
    /** DB filename */
    private static final String DATABASE_NAME = "quizDb.db";
    public quizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    /**
     * DB does not exist. Create it
     * @param db will contain the newly created database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute SQL command to create a new bug database
        db.execSQL("create table " + QuizTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                QuizTable.Cols.UUID + ", " +
                QuizTable.Cols.QUESTION + ", " +
                QuizTable.Cols.ANSWER +
                ")");
    }
    /**
     * Previous DB is older and needs to be upgraded to current version.
     * @param db is the database that needs to be upgraded
     * @param oldVersion is the version number of the DB.
     * @param newVersion is the version the DB should be upgraded to.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
