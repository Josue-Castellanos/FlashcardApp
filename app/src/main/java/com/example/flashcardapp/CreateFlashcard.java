package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateFlashcard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flashcard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Null check for the active deck
        FlashcardDeck activeDeck = MainActivity.flashcardsManager.getActiveDeck();
        if (activeDeck != null) {
            // Get the custom title TextView
            TextView deckTitle = findViewById(R.id.deckTitle);
            if (deckTitle != null) {
                // Set a custom title text
                deckTitle.setText(MainActivity.flashcardsManager.getActiveDeck().toString());
            }
        }

        // Add button listener
        Button play = findViewById(R.id.playButton);
        Button add = findViewById(R.id.addButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonActivity();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButtonActivity();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addButtonActivity() {
        // pull question and answer from EditText box
        EditText questionEditText = findViewById(R.id.questionText);
        EditText answerEditText = findViewById(R.id.answerText);
        String question = questionEditText.getText().toString();
        String answer = answerEditText.getText().toString();

        // create flashcard, add new card to the flashcard list
        FlashcardDeck activeDeck = MainActivity.flashcardsManager.getActiveDeck();
        if (activeDeck != null) {
            activeDeck.addFlashcard(new Flashcard(answer, question));
            // save changes
            MainActivity.preferencesManager.saveObjectList(MainActivity.flashcardsManager.getDecks());
            // clear the TextView boxes to add another card
            ((EditText) findViewById(R.id.questionText)).setText("");
            ((EditText) findViewById(R.id.answerText)).setText("");
        }
    }

    public void playButtonActivity() {
        Intent intent = new Intent(this, PlayFlashcards.class);
        startActivity(intent);
    }
}