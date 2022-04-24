package edu.andrews.cptr252.andreastanko.finalproject.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;
import edu.andrews.cptr252.andreastanko.finalproject.Question_main;
import edu.andrews.cptr252.andreastanko.finalproject.database.quizDbSchema.QuizTable;

/**
 * Handle results from DB queries
 */
public class quizDbCursorWrapper extends CursorWrapper {
    public quizDbCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    /**
     * Extract bug information from a query.
     * @return Bug that resulted from the query.
     */
    public Question_main getQuestion() {
        String uuidString = getString(getColumnIndex(QuizTable.Cols.UUID));
        String title = getString(getColumnIndex(QuizTable.Cols.QUESTION));
        boolean answer = Boolean.parseBoolean(getString(getColumnIndex(String.valueOf(QuizTable.Cols.ANSWER))));
        Question_main q = new Question_main(UUID.fromString(uuidString));
        q.setTitle(title);
        q.setAnswer(answer);

        return q;
    }
}

