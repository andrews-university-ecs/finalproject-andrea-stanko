package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

public class QuizModeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new QuizModeFragment();
    }

}