package edu.andrews.cptr252.andreastanko.finalproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;
import edu.andrews.cptr252.andreastanko.finalproject.Question;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbSchema.quizTable;
/**
 * Handle results from DB queries
 */
public class quizCursorWrapper extends CursorWrapper {
    public quizCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    /**
     * Extract bug information from a query.
     * @return Bug that resulted from the query.
     */
    public Question getQuestion() {
        String uuidString = getString(getColumnIndex(quizTable.Cols.UUID));
        String title = getString(getColumnIndex(quizTable.Cols.QUESTION));
        int isSolved = getInt(getColumnIndex(quizTable.Cols.ANSWER));
        Question q = new Question(UUID.fromString(uuidString));
        q.setTitle(title);
        q.setAnswer(isSolved != 0);
        return q;
    }
}