package edu.andrews.cptr252.andreastanko.finalproject;

import androidx.fragment.app.Fragment;

public class QuizMenuActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new edu.andrews.cptr252.andreastanko.finalproject.QuizMenuFragment();
    }

}
