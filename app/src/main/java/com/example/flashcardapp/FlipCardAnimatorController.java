package com.example.flashcardapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.widget.TextView;

public class FlipCardAnimatorController {
    private final AnimatorSet frontAnimator;
    private final AnimatorSet backAnimator;

    private boolean isFront = true;

    // Constructor
    public FlipCardAnimatorController(TextView frontTextView, TextView backTextView) {
        // Load the flip animations from the XML resource files
        // Now we will set the front and back animation
        backAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(frontTextView.getContext(),
                R.animator.back_animator);
        frontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(backTextView.getContext(),
                R.animator.front_animator);
    }

    public void onFlipToFront(TextView frontCardView, TextView backCardView) {
        if (backAnimator != null) {
            frontAnimator.setTarget(backCardView);
            backAnimator.setTarget(frontCardView);
            backAnimator.start();
            frontAnimator.start();
            isFront = true;
        }
    }
    public void onFlipToBack(TextView frontCardView, TextView backCardView) {
        if (frontAnimator != null) {
            frontAnimator.setTarget(frontCardView);
            backAnimator.setTarget(backCardView);
            frontAnimator.start();
            backAnimator.start();
            isFront = false;
        }
    }
    public boolean getFlashcardBoolean() {
        return isFront;
    }
}
