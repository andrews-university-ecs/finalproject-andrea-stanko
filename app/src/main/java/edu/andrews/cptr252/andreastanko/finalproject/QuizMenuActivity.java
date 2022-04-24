package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

public class QuizMenuActivity extends edu.andrews.cptr252.andreastanko.finalproject.SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new QuizMenuFragment();
    }

}
