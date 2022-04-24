package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionList {
    private static QuestionList sOurInstance;

    private static final String TAG ="Buglist";
    private static final String FILENAME = "questions.json";
    private QuestionJSONSerializer mSerializer;

    public boolean saveQuestions(){
        try{
            mSerializer.saveQuestions(mQuestion_mains);
            Log.d(TAG,"Bugs saved to file");
            return true;
        }catch (Exception e){
            Log.e(TAG, "Error saving bugs: " + e);
            return false;
        }
    }



    public Question_main getQuestion(UUID id){
        for(Question_main q : mQuestion_mains){
            if(q.getId().equals(id))
                return q;
        }
        return null;
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
        mQuestion_mains.add(q);
    }

    private QuestionList(Context appContext) {
        mAppContext = appContext;
        mSerializer = new QuestionJSONSerializer(mAppContext, FILENAME);

        try{
            mQuestion_mains = mSerializer.loadQuestions();
        } catch (Exception e){
            mQuestion_mains = new ArrayList<>();
            Log.e(TAG, "Error loading bugs: " + e);
        }
    }

    public ArrayList<Question_main> getQuestions() {return mQuestion_mains;}

    public void addQuestion(int position, Question_main q){
        mQuestion_mains.add(position, q);
        saveQuestions();
    }

    public void deleteQuestion(int position){
        mQuestion_mains.remove(position);
        saveQuestions();
    }

}

