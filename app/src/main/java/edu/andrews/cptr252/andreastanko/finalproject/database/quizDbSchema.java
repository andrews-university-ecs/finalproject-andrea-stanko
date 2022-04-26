package edu.andrews.cptr252.andreastanko.finalproject.database;

public class quizDbSchema {
    public static final class quizTable {
        public static final String NAME = "quiz";
        /**
         * DB column names.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String QUESTION = "question";
            public static final String ANSWER = "answer";
        }
    }

}
