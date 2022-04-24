package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class QuestionJSONSerializer {
    private Context mContext;
    private String mFilename;

    public QuestionJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }
    /**
     * Load list of bugs from JSON file to local device
     * @return list of bugs
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<edu.andrews.cptr252.andreastanko.finalproject.Question_main> loadQuestions() throws IOException, JSONException{
        ArrayList<edu.andrews.cptr252.andreastanko.finalproject.Question_main> Question_mains = new ArrayList<>();
        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                jsonString.append(line);
            }

            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for(int i =0; i < array.length(); i++){
                Question_mains.add(new edu.andrews.cptr252.andreastanko.finalproject.Question_main(array.getJSONObject(i)));
            }
        }catch(FileNotFoundException e){

        } finally {
            if(reader != null)
                reader.close();
        }
        return Question_mains;
    }

    public void saveQuestions(ArrayList<edu.andrews.cptr252.andreastanko.finalproject.Question_main> Question_mains) throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(edu.andrews.cptr252.andreastanko.finalproject.Question_main q: Question_mains){
            array.put(q.toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null)
                writer.close();
        }
    }



}
