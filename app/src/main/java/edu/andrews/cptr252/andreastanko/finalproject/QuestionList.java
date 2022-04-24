package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbCursorWrapper;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbHelper;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbSchema.QuizTable;
import java.util.ArrayList;
import java.util.UUID;

public class QuestionList {
    private static QuestionList sOurInstance;
    /** SQLite DB where bugs are stored */
    private SQLiteDatabase mDatabase;


    private static final String TAG ="QuestionsList";
    private static final String FILENAME = "questions.json";
    private QuestionJSONSerializer mSerializer;

    /**
     * Pack bug information into a ContentValues object.
     * @param q to pack.
     * @return resulting ContentValues object
     */
    public static ContentValues getContentValues(Question_main q) {
        ContentValues values = new ContentValues();
        values.put(QuizTable.Cols.UUID, q.getId().toString());
        values.put(QuizTable.Cols.QUESTION, q.getTitle());
        values.put(String.valueOf(QuizTable.Cols.ANSWER), q.getAnswer());
        return values;
    }
    /**
     * Build a query for Bug DB.
     * @param whereClause defines the where clause of a SQL query
     * @param whereArgs defines where arguments for a SQL query
     * @return Object defining a SQL query
     */
    private quizDbCursorWrapper queryQuestions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                QuizTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new quizDbCursorWrapper(cursor);
    }



    public Question_main getQuestion(UUID id){
        quizDbCursorWrapper cursor = queryQuestions(QuizTable.Cols.UUID + " = ? ",
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
    private ArrayList<Question_main> mQuestion_mains;

    private Context mAppContext;

    public void addQuestion(Question_main q){
        ContentValues values = getContentValues(q);
        mDatabase.insert(QuizTable.NAME, null, values);
    }

    private QuestionList(Context appContext) {

        mAppContext = appContext.getApplicationContext();
        // Open DB file or create it if it does not already exist.
        // If the DB is older version, onUpgrade will be called.
        mDatabase = new quizDbHelper(mAppContext).getWritableDatabase();

    }

    public ArrayList<Question_main> getQuestions() {
        ArrayList<Question_main> q = new ArrayList<>();
        quizDbCursorWrapper cursor = queryQuestions(null, null);
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
    /**
     * Update information for a given bug.
     * @param q contains the latest information for the bug.
     */
    public void updateQuestion(Question_main q) {
        String uuidString = q.getId().toString();
        ContentValues values = getContentValues(q);
        mDatabase.update(QuizTable.NAME, values,
                QuizTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }


    public void deleteQuestion(Question_main q) {
        String uuidString = q.getId().toString();
        mDatabase.delete(QuizTable.NAME,
                QuizTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }

}

