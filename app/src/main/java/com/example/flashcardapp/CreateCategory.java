package com.example.flashcardapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateCategory extends AppCompatActivity {
    private EditText titleEditText;
    FlashcardDeck new_deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FLASHCARD DECKS");

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        titleEditText = findViewById(R.id.titleText);
        Button createCategoryButton = findViewById(R.id.createButton);
        createCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDeck();
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
    public void createDeck(){
        Toast.makeText(this, "Created Deck: " +titleEditText.getText().toString(), Toast.LENGTH_SHORT).show();
        new_deck = new FlashcardDeck();
        new_deck.setDeckName(titleEditText.getText().toString());
        MainActivity.flashcardsManager.addDeck(new_deck);
        MainActivity.preferencesManager.saveObjectList(MainActivity.flashcardsManager.getDecks());
        finish();
    }
}
