package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo, splashImg, appName;

    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);


        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.name);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        splashImg.animate().translationY(-3000).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(4300);

        // Delay the start of MainActivity using Handler and include slide transition
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);

                // Create a Bundle for the transition options
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(IntroductoryActivity.this, findViewById(R.id.logo), "logoTransition").toBundle();

                // Set up slide transition
                Slide slide = new Slide(Gravity.START);
                slide.setDuration(500); // Set the duration of the slide transition
                getWindow().setExitTransition(slide);

                // Start MainActivity with the slide transition
                startActivity(intent, bundle);
                // Close this activity to prevent going back to it
                finish();
            }
        }, 5300); // 5300 milliseconds delay (4300 + 1000 for animations)
    }
}