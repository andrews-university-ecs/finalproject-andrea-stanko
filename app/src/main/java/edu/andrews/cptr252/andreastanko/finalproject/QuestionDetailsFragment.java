package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailsFragment extends Fragment {
    public static final String TAG = "QuestionDetailsFragment";
    private Question mQuestion;
    private EditText mTitleField;
    private RadioButton mTrueButton;
    private RadioButton mFalseButton;
    private RadioButton mButtonGroup;

    public static final String EXTRA_QUESTION_ID
            = "edu.andrews.cptr252.andreastanko.finalproject.question_id";


    /** Required interface to be implemented in hosting activities */
    public interface Callbacks {
        void onQuestionUpdated(Question q);
    }
    private Callbacks mCallbacks;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    public void updateQuestion() {
        QuestionList.getInstance(getActivity()).updateQuestion(mQuestion);
        mCallbacks.onQuestionUpdated(mQuestion);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID questionId = (UUID)getArguments().getSerializable(edu.andrews.cptr252.andreastanko.finalproject.QuestionAdapter.EXTRA_QUESTION_ID);
        mQuestion = QuestionList.getInstance(getActivity()).getQuestion(questionId);
    }

    public QuestionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_details, container, false);

        mTrueButton = v.findViewById(R.id.true_button);
        mFalseButton = v.findViewById(R.id.false_button);
        if(mQuestion.getAnswer() == true){
            mTrueButton.setChecked(true);
        }
        if(mQuestion.getAnswer() == false){
            mFalseButton.setChecked(true);
        }
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setChecked(true);
                mQuestion.setAnswer(true);
                Log.d(TAG, "Answer changed to "+ mQuestion.getAnswer());
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFalseButton.setChecked(true);
                mQuestion.setAnswer(false);
                Log.d(TAG, "Answer changed to "+ mQuestion.getAnswer());
            }
        });


        mTitleField = v.findViewById(R.id.question_title);
        mTitleField.setText(mQuestion.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQuestion.setTitle(s.toString());
                Log.d(TAG, "Title changed to "+ mQuestion.getTitle());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    public static QuestionDetailsFragment newInstance(UUID questionId){
        Bundle args = new Bundle();
        args.putSerializable(edu.andrews.cptr252.andreastanko.finalproject.QuestionAdapter.EXTRA_QUESTION_ID, questionId);

        QuestionDetailsFragment fragment = new QuestionDetailsFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause(){
        super.onPause();
        QuestionList.getInstance(getActivity()).updateQuestion(mQuestion);
    }
}
