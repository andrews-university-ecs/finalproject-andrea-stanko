package edu.andrews.cptr252.andreastanko.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends Fragment {
    /**
     * Tag for message log
     */
    private static final String TAG = "QuestionListFragment";


    private ArrayList<Question> mQuestion_s;

    /**
     * Recyler view that displays list of quesitons
     */
    private RecyclerView mRecyclerView;

    private  QuestionAdapter mQuestionAdapter;

    public QuestionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Question List");
        mQuestion_s = QuestionList.getInstance(getActivity()).getQuestions();

        mQuestionAdapter = new QuestionAdapter(mQuestion_s, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        mRecyclerView = v.findViewById(R.id.question_list_recyclerView);
        mRecyclerView.setAdapter(mQuestionAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        edu.andrews.cptr252.andreastanko.finalproject.QuestionSwiper bugSwiper = new edu.andrews.cptr252.andreastanko.finalproject.QuestionSwiper(mQuestionAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(bugSwiper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        mQuestionAdapter.refreshQuestionListDisplay();

    }

    /** Required interface to be implemented in hosting activities */
    public interface Callbacks {
        void onQuestionSelected(Question q);
    }
    private Callbacks mCallbacks;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
        if (mQuestionAdapter != null) {
            mQuestionAdapter.setCallbacks(mCallbacks);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mQuestionAdapter.setCallbacks(mCallbacks);
    }
    /** Update bug list display */
    public void updateUI() {
        mQuestionAdapter.refreshQuestionListDisplay();
    }

    private void addQuestion(){
        Question q = new Question();
        QuestionList.getInstance(getActivity()).addQuestion(q);

        mCallbacks.onQuestionSelected(q);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_question_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.question_list_menu_add_question_menu:
                addQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
