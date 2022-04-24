package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class QuestionDetailsActivity extends edu.andrews.cptr252.andreastanko.finalproject.SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID questionId = (UUID)getIntent().getSerializableExtra(QuestionConverter.EXTRA_QUESTION_ID);
        return edu.andrews.cptr252.andreastanko.finalproject.QuestionDetailsFragment.newInstance(questionId);
    }

}
