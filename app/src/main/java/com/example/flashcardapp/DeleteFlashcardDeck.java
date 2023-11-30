package com.example.flashcardapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class DeleteFlashcardDeck extends AppCompatActivity {
    ArrayAdapter<FlashcardDeck> slideAdapter;
    FlashcardDeck deckToRemove = new FlashcardDeck();
    List<FlashcardDeck> deckList;
    ListView listView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_flashcard_deck);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // listview
        listView = findViewById(R.id.flashcardListView);
        // retrieve saved shared preference
        deckList = MainActivity.preferencesManager.getObjectList();
        MainActivity.flashcardsManager.setDecks(deckList);
        // set adapter
        slideAdapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);
        listView.setAdapter(slideAdapter);
        listView.setOnTouchListener(new SlideListAnimatorController(DeleteFlashcardDeck.this, listView) {
            public void onSwipeLeft(int pos) {

                // Handle the swipe action here
                deckToRemove = slideAdapter.getItem(pos);
                slideAdapter.remove(deckToRemove);
                MainActivity.flashcardsManager.removeDeck(deckToRemove); // Update your data source as well
                MainActivity.preferencesManager.saveObjectList(deckList); // Save the updated list
                slideAdapter.notifyDataSetChanged();

                Toast.makeText(DeleteFlashcardDeck.this, "Deck Deleted: " +deckToRemove.toString(), Toast.LENGTH_SHORT).show();


            }
        });

        Button delete = findViewById(R.id.deleteFlashcardDeckButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call function
                doneEditing();
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
    public void doneEditing () {
        finish();
    }
}