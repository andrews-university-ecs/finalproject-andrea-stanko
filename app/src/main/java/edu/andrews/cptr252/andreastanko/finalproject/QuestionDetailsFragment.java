package edu.andrews.cptr252.andreastanko.finalproject;

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
    private Question_main mQuestion_main;
    private EditText mTitleField;
    private RadioButton mTrueButton;
    private RadioButton mFalseButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID questionId = (UUID)getArguments().getSerializable(QuestionConverter.EXTRA_QUESTION_ID);
        mQuestion_main = edu.andrews.cptr252.andreastanko.finalproject.QuestionList.getInstance(getActivity()).getQuestion(questionId);
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
        if(mQuestion_main.getAnswer() == true){
            mTrueButton.setChecked(true);
        }
        if(mQuestion_main.getAnswer() == false){
            mFalseButton.setChecked(true);
        }
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrueButton.setChecked(true);
                mQuestion_main.setAnswer(true);
                Log.d(TAG, "Answer changed to "+ mQuestion_main.getAnswer());
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFalseButton.setChecked(true);
                mQuestion_main.setAnswer(false);
                Log.d(TAG, "Answer changed to "+ mQuestion_main.getAnswer());
            }
        });


        mTitleField = v.findViewById(R.id.question_title);
        mTitleField.setText(mQuestion_main.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQuestion_main.setTitle(s.toString());
                Log.d(TAG, "Title changed to "+ mQuestion_main.getTitle());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    public static QuestionDetailsFragment newInstance(UUID questionId){
        Bundle args = new Bundle();
        args.putSerializable(QuestionConverter.EXTRA_QUESTION_ID, questionId);

        QuestionDetailsFragment fragment = new QuestionDetailsFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause(){
        super.onPause();
        edu.andrews.cptr252.andreastanko.finalproject.QuestionList.getInstance(getActivity()).saveQuestions();
    }
}
