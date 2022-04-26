package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizCursorWrapper;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbHelper;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbSchema.quizTable;

public class QuestionList {
    /** SQLite DB where bugs are stored */
    private SQLiteDatabase mDatabase;
    private static QuestionList sOurInstance;
    private static final String TAG ="Buglist";
    private static final String FILENAME = "questions.json";
    private QuestionJSONSerializer mSerializer;

    /**
     * Pack bug information into a ContentValues object.
     * @param q to pack.
     * @return resulting ContentValues object
     */
    public static ContentValues getContentValues(Question q) {
        ContentValues values = new ContentValues();
        values.put(quizTable.Cols.UUID, q.getId().toString());
        values.put(quizTable.Cols.QUESTION, q.getTitle());
        values.put(quizTable.Cols.ANSWER, q.getAnswer() ? 1 : 0);
        return values;
    }


    /**
     * Build a query for Bug DB.
     * @param whereClause defines the where clause of a SQL query
     * @param whereArgs defines where arguments for a SQL query
     * @return Object defining a SQL query
     */
    private quizCursorWrapper queryBugs(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                quizTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new quizCursorWrapper(cursor);
    }




    public Question getQuestion(UUID id){
        quizCursorWrapper cursor = queryBugs(quizTable.Cols.UUID + " = ? ",
                new String[] { id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQuestion();
        } finally {
            cursor.close();
        }
    }

    public static QuestionList getInstance(Context c) {
        if(sOurInstance == null) {
            sOurInstance = new QuestionList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    /**array list of questions
     *
     */

    private Context mAppContext;


    /**
     * Update information for a given bug.
     * @param q contains the latest information for the bug.
     */
    public void updateQuestion(Question q) {
        String uuidString = q.getId().toString();
        ContentValues values = getContentValues(q);
        mDatabase.update(quizTable.NAME, values,
                quizTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }

    private QuestionList(Context appContext) {
        mAppContext = appContext.getApplicationContext();
        // Open DB file or create it if it does not already exist.
        // If the DB is older version, onUpgrade will be called.
        mDatabase = new quizDbHelper(mAppContext).getWritableDatabase();
    }

    public ArrayList<Question> getQuestions() {

        ArrayList<Question> q = new ArrayList<>();
        quizCursorWrapper cursor = queryBugs(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                q.add(cursor.getQuestion());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return q;

    }

public void addQuestion(Question q){
    ContentValues values = getContentValues(q);
    mDatabase.insert(quizTable.NAME, null, values);

}

    public void deleteQuestion(Question q){
        String uuidString = q.getId().toString();
        mDatabase.delete(quizTable.NAME,
                quizTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );

    }

}

