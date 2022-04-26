package edu.andrews.cptr252.andreastanko.finalproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionSwiper extends ItemTouchHelper.SimpleCallback {

    private edu.andrews.cptr252.andreastanko.finalproject.QuestionAdapter mAdapter;
    private Drawable mIcon;
    private final ColorDrawable mBackground;

    public QuestionSwiper(edu.andrews.cptr252.andreastanko.finalproject.QuestionAdapter adapter){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mIcon = ContextCompat.getDrawable(mAdapter.getActivty(),android.R.drawable.ic_menu_delete);
        mBackground = new ColorDrawable(Color.rgb(33,150,243));
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targer){
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        int deletedPosition = viewHolder.getAdapterPosition();

        mAdapter.deleteQuestion(deletedPosition);
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int iconMargin = (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - mIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + mIcon.getIntrinsicHeight();
        int iconLeft = itemView.getRight() - iconMargin - mIcon.getIntrinsicWidth();
        int iconRight = itemView.getRight() - iconMargin;
        mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

        if(dX > 0){
            mBackground.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
        } else if(dX < 0){
            mBackground.setBounds(itemView.getRight() + ((int) dX),itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else {
            mIcon.setBounds(0,0,0,0);
            mBackground.setBounds(0,0,0,0);
        }

        mBackground.draw(c);
        mIcon.draw(c);
    }






}