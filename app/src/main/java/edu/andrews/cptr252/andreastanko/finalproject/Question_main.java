package edu.andrews.cptr252.andreastanko.finalproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Question_main {
    /**
     * UUID of the question, a beautiful unique identifier that may or may not be needed
     */
    private UUID mId;

    /**
     * The title of the question, the question if you will
     */
    private String mTitle;

    /**
     * The answer of the question, true/false
     */
    private boolean mAnswer;

    /**
     * JSON atribute for id
     */
    private static final String JSON_ID ="id";
    /**
     * JSON atribute for title
     */
    private static final String JSON_TITLE ="title";
    /**
     * Json atribute for answer
     */
    private static final String JSON_ANSWER ="answer";

    /**
     * Created new question for json object
     * @param json json question object
     * @throws JSONException
     */
    public Question_main(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.optString(JSON_TITLE);
        mAnswer = json.optBoolean(JSON_ANSWER);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_TITLE, mTitle);
        jsonObject.put(JSON_ANSWER, mAnswer);

        return jsonObject;
    }



    /**
     * The constructer for the question
     */
    public Question_main(){
        this(UUID.randomUUID());
    }
    public Question_main(UUID id) {
        mId = id;
    }

    /**
     * Returns the ID of the question
     * @return mID id of the question
     */
    public UUID getId(){
        return mId;
    }


    /**
     * Setters and getters for the title and answer
     */
    public String getTitle(){return mTitle; }
    public void setTitle(String title){ mTitle = title; }
    public boolean getAnswer(){return Boolean.parseBoolean(String.valueOf(mAnswer));}
    public void setAnswer(boolean answer){mAnswer = answer;}
}
