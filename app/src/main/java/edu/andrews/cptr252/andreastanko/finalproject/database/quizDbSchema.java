package edu.andrews.cptr252.andreastanko.finalproject.database;

public class quizDbSchema {
    public static final class QuizTable {
        public static final String NAME = "questions";
        /**
         * DB column names.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String QUESTION = "question";
            public static final boolean ANSWER = true;
        }
    }

}
