package edu.andrews.cptr252.andreastanko.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class QuestionConverter extends RecyclerView.Adapter<QuestionConverter.ViewHolder> {
    public static final String TAG ="QuestionAdapter";
    public static final String EXTRA_QUESTION_ID = "edu.andrews.cptr252.andreastanko.finalproject.question_id";

    /**
     * Array list of questions, our quiz if you will
     */
    private ArrayList<edu.andrews.cptr252.andreastanko.finalproject.Question_main> mQuestion_mains;

    private Activity mActivity;

    /**
     * Nice constructer of the questionAdapter
     * @param Question_mains Arraylist of questions, singleton class
     */
    public QuestionConverter(ArrayList<edu.andrews.cptr252.andreastanko.finalproject.Question_main> Question_mains, Activity activity){
        mActivity = activity;
        mQuestion_mains = Question_mains;
    }

    public Context getActivty(){
        return mActivity;
    }

    private void showUndoSnachbar(final edu.andrews.cptr252.andreastanko.finalproject.Question_main q, final int position){
        View view = mActivity.findViewById(android.R.id.content);
        String bugDeletedText = mActivity.getString(R.string.question_deleted_msg, q.getTitle());
        Snackbar snackbar = Snackbar.make(view, bugDeletedText, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.undo_option, new View.OnClickListener() {
            @Override
            public void onClick(View view){

                restoreQuestion(q, position);
            }
        });

        snackbar.setActionTextColor(Color.BLUE);

        snackbar.show();
    }

    public void deleteQuestion(int position){
        final edu.andrews.cptr252.andreastanko.finalproject.Question_main q = mQuestion_mains.get(position);
        edu.andrews.cptr252.andreastanko.finalproject.QuestionList.getInstance(mActivity).deleteQuestion(position);
        notifyItemRemoved(position);
        showUndoSnachbar(q,position);
    }

    public void restoreQuestion(edu.andrews.cptr252.andreastanko.finalproject.Question_main q, int position){
        edu.andrews.cptr252.andreastanko.finalproject.QuestionList.getInstance(mActivity).addQuestion(position,q);
        notifyItemInserted(position);
    }



    /**
     * Creating a viewholder with all its things(Honestly i dont know what this does/why)
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        /**
         * Text view that displays the question
         */
        public TextView questionTextView;

        /**
         * True button
         */
        public RadioButton trueButton;
        /**
         * False button
         */
        public RadioButton falseButton;

        public Context context;

        /**
         * View holder constructer
         * @param itemView a View
         */
        public ViewHolder(View itemView){
            super(itemView);

            /**
             * Getting all of the refrences to the buttons and text view
             */
            questionTextView = itemView.findViewById(R.id.question_list_item_titleTextView);
            trueButton = itemView.findViewById(R.id.question_list_item_trueRadioButton);
            falseButton = itemView.findViewById(R.id.question_list_item_falseRadioButton);

            context = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            /**
             * Getting the postion of the question adapter
             */
            int position = getAdapterPosition();


            if(position != RecyclerView.NO_POSITION){
                edu.andrews.cptr252.andreastanko.finalproject.Question_main q = mQuestion_mains.get(position);
                /**
                 * Creating an intent to launch the question details when a question is clicked
                 */
                Intent i = new Intent(context, edu.andrews.cptr252.andreastanko.finalproject.QuestionDetailsActivity.class);
                i.putExtra(QuestionConverter.EXTRA_QUESTION_ID, q.getId());
                context.startActivity(i);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View questionView = inflater.inflate(R.layout.list_item_question, parent, false);

        ViewHolder viewHolder = new ViewHolder(questionView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        edu.andrews.cptr252.andreastanko.finalproject.Question_main q = mQuestion_mains.get(position);

        TextView questionTextView = viewHolder.questionTextView;
        RadioButton trueButton = viewHolder.trueButton;
        RadioButton falseButton = viewHolder.falseButton;

        questionTextView.setText(q.getTitle());
        trueButton.setChecked(q.getAnswer());
        falseButton.setChecked(!q.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mQuestion_mains.size();
    }

}
