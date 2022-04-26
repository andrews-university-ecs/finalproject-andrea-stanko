package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class QuestionDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID questionId = (UUID)getIntent().getSerializableExtra(edu.andrews.cptr252.andreastanko.finalproject.QuestionAdapter.EXTRA_QUESTION_ID);
        return edu.andrews.cptr252.andreastanko.finalproject.QuestionDetailsFragment.newInstance(questionId);
    }

}
