package com.example.flashcardapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SlideListAnimatorController implements View.OnTouchListener{
    private final GestureDetector gestureDetector;
    private final ListView listView;

    public SlideListAnimatorController(Context context, ListView listView) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.listView = listView;
    }
    public void onSwipeLeft(int pos) {

    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                int pos = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
                onSwipeLeft(pos);
                return true;
            }
            return false;
        }
    }
}
