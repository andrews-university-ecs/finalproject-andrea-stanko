package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

public class QuizModeActivity extends edu.andrews.cptr252.andreastanko.finalproject.SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new edu.andrews.cptr252.andreastanko.finalproject.QuizModeFragment();
    }

}