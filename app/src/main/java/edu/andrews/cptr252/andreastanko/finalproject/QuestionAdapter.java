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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    public static final String TAG ="QuestionAdapter";
    public static final String EXTRA_QUESTION_ID = "edu.andrews.cptr252.brodis.quizApp.question_id";

    /**
     * Array list of questions, our quiz if you will
     */
    private ArrayList<Question> mQuestion_s;

    private Activity mActivity;

    private QuestionListFragment.Callbacks mCallbacks;
    public void setCallbacks(QuestionListFragment.Callbacks callbacks) {
        mCallbacks = callbacks;
    }


    /**
     * Nice constructer of the questionAdapter
     * @param question_s Arraylist of questions, singleton class
     */
    public QuestionAdapter(ArrayList<Question> question_s, Activity activity){
        mActivity = activity;
        mQuestion_s = question_s;
        setCallbacks((QuestionListFragment.Callbacks) mActivity);

    }

    public Context getActivty(){
        return mActivity;
    }

    private void showUndoSnachbar(final Question q, final int position){
        View view = mActivity.findViewById(android.R.id.content);
        String bugDeletedText = mActivity.getString(R.string.question_deleted_msg, q.getTitle());
        Snackbar snackbar = Snackbar.make(view, bugDeletedText, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.undo_option, new View.OnClickListener() {
            @Override
            public void onClick(View view){

                restoreQuestion(q, position);
            }
        });

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    // Officially delete bug from bug list
                    QuestionList.getInstance(mActivity).deleteQuestion(q);
                }
            }
        });


        snackbar.setActionTextColor(Color.BLUE);

        snackbar.show();
    }

    public void deleteQuestion(int position){
        final Question q = mQuestion_s.get(position);

        mQuestion_s.remove(position);

        notifyItemRemoved(position);
        showUndoSnachbar(q,position);
    }
    /** Force adapter to load new bug list and regenerate views. */
    public void refreshQuestionListDisplay() {
        mQuestion_s = QuestionList.getInstance(mActivity).getQuestions();
        notifyDataSetChanged();
    }

    public void restoreQuestion(Question q, int position){
        refreshQuestionListDisplay();

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
                Question q = mQuestion_s.get(position);
                /**
                 * Creating an intent to launch the question details when a question is clicked
                 */
                Intent i = new Intent(context, edu.andrews.cptr252.andreastanko.finalproject.QuestionDetailsActivity.class);
                i.putExtra(QuestionAdapter.EXTRA_QUESTION_ID, q.getId());
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
        Question q = mQuestion_s.get(position);

        TextView questionTextView = viewHolder.questionTextView;
        RadioButton trueButton = viewHolder.trueButton;
        RadioButton falseButton = viewHolder.falseButton;

        questionTextView.setText(q.getTitle());

        trueButton.setChecked(q.getAnswer());
        falseButton.setChecked(!q.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mQuestion_s.size();
    }

}
