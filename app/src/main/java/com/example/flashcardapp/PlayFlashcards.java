package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
// Disable next and previous and random button if the list has only 1 flashcard
public class PlayFlashcards extends AppCompatActivity {
    private static final int EDIT_FLASHCARD_REQUEST_CODE = 100;

    FlipCardAnimatorController flipCardAnimatorController;
    Button random, previous, next;
    TextView front, back, count;
    String currQuestion, currAnswer;
    int currIndex = 0;
    int deckSize = MainActivity.flashcardsManager.getActiveDeckSize();
    float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_flashcards);

        // Set the title text
        currIndex = MainActivity.flashcardsManager.getCurrentFlashcardIndex() + 1;
        count = findViewById(R.id.flashcard_count_view);
        count.setText(currIndex + "/" + deckSize);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            FlashcardDeck activeDeck = MainActivity.flashcardsManager.getActiveDeck();
            if (activeDeck != null) {
                String title = activeDeck.toString();
                if (title != null) {
                    actionBar.setTitle(title);

                    // Remove the default title
                    actionBar.setDisplayShowTitleEnabled(false);

                    // Get the custom title TextView
                    TextView deckTitle = findViewById(R.id.deckTitle);
                    if (deckTitle != null) {
                        // Set a custom title text
                        deckTitle.setText(MainActivity.flashcardsManager.getActiveDeck().toString());
                    }
                }
            }
        }

        // Now Create Animator Objects and Flashcard Manager Object
        // Now we will add the animator to our card
        Flashcard currentFlashcard = MainActivity.flashcardsManager.getCurrentFlashcard();
        if (currentFlashcard != null) {
            currQuestion = currentFlashcard.getQuestion();
            currAnswer = currentFlashcard.getAnswer();
        } else {
            // Handle the case where there is no current flashcard
            // You might want to show a message or take appropriate action
            Log.d("PlayFlashcards", "No current flashcard found");
        }

        // we now need to modify the camera scale
        scale = getResources().getDisplayMetrics().density;

        front = findViewById(R.id.frontTextView);
        back = findViewById(R.id.backTextView);
        front.setText(currQuestion);
        back.setText(currAnswer);

        random = findViewById(R.id.randomButton);
        previous = findViewById(R.id.previousButton);
        next = findViewById(R.id.nextButton);

        front.setCameraDistance(8000 * scale);
        back.setCameraDistance(8000 * scale);

        flipCardAnimatorController = new FlipCardAnimatorController(front, back);
        // Call the method to update the state of the Random button
        updateRandomButtonState();

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random_card();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_card();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous_card();
            }
        });
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip_card();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip_card();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    // Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("lifecycle","onCreationOptionsMenu invoked");
        getMenuInflater().inflate(R.menu.playing_flashcard_option_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up button
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (itemId == R.id.home) {
            return_main();
            return true;
        }
        else if (itemId == R.id.delete_card) {
            delete_card();
            return true;
        }
        else if (itemId == R.id.edit_card) {
            edit_card();
            return true;
        }
        else if (itemId == R.id.add_card) {
            add_card();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void edit_card() {
        Intent intent = new Intent(this, EditFlashcard.class);
        startActivityForResult(intent, EDIT_FLASHCARD_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_FLASHCARD_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle the result from the EditFlashcard activity
            // Update the displayed flashcard and count
            updateFlashcardAndCount();
        }
    }
    // Method to update the displayed flashcard and count
    private void updateFlashcardAndCount() {
        currQuestion = MainActivity.flashcardsManager.getCurrentFlashcard().getQuestion();
        currAnswer = MainActivity.flashcardsManager.getCurrentFlashcard().getAnswer();
        front.setText(currQuestion);
        back.setText(currAnswer);

        // Set the title text
        currIndex = MainActivity.flashcardsManager.getCurrentFlashcardIndex() + 1;
        count.setText(currIndex + "/" + deckSize);

        // Update the state of the Random button
        updateRandomButtonState();
    }

    public void previous_card() {
        currIndex--;
        if (currIndex == 0) {
            Toast.makeText(this, "End Of List", Toast.LENGTH_SHORT).show();
            currIndex = MainActivity.flashcardsManager.restIndex();
            currQuestion = MainActivity.flashcardsManager.getActiveDeck().getFlashcards().get(currIndex).getQuestion();
            currIndex++;
        }
        else {
            currQuestion = MainActivity.flashcardsManager.getPreviousFlashcard().getQuestion();
        }
        currAnswer = MainActivity.flashcardsManager.getCurrentFlashcard().getAnswer();
        front.setText(currQuestion);
        back.setText(currAnswer);

        count.setText((currIndex) + "/" + deckSize);
    }
    public void next_card() {
        currIndex++;
        if (currIndex == deckSize + 1) {
            Toast.makeText(this, "Start Of List", Toast.LENGTH_SHORT).show();
            currIndex = MainActivity.flashcardsManager.restIndex();
            currQuestion = MainActivity.flashcardsManager.getActiveDeck().getFlashcards().get(currIndex).getQuestion();
        }
        else {
            currQuestion = MainActivity.flashcardsManager.getNextFlashcard().getQuestion();
        }
        currAnswer = MainActivity.flashcardsManager.getCurrentFlashcard().getAnswer();
        front.setText(currQuestion);
        back.setText(currAnswer);
        if (currIndex == 0) {
            currIndex = 1;
        }
        count.setText(currIndex + "/" + deckSize);
    }
    public void random_card () {
        currQuestion = MainActivity.flashcardsManager.getRandomFlashcard().getQuestion();
        currAnswer = MainActivity.flashcardsManager.getCurrentFlashcard().getAnswer();
        front.setText(currQuestion);
        back.setText(currAnswer);

        // Set the title text
        currIndex = MainActivity.flashcardsManager.getCurrentFlashcardIndex();
        count.setText((currIndex + 1) + "/" + deckSize);
        currIndex++;
        updateRandomButtonState();
    }
    public void return_main() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void flip_card() {
        if (flipCardAnimatorController.getFlashcardBoolean()) {
            flipCardAnimatorController.onFlipToBack(front, back);
        } else {
            flipCardAnimatorController.onFlipToFront(front, back);
        }
    }
    public void delete_card() {
        MainActivity.flashcardsManager.getActiveDeck().removeFlashcard(MainActivity.flashcardsManager.getCurrentFlashcard());
        FlashcardDeck tmpDeck = MainActivity.flashcardsManager.getActiveDeck();
        deckSize = tmpDeck.getFlashcards().size();
        // save changes
        MainActivity.preferencesManager.saveObjectList(MainActivity.flashcardsManager.getDecks());
        if (deckSize == 0) {
            return_main();
            Toast.makeText(this, "Empty List: "+ tmpDeck.toString(), Toast.LENGTH_SHORT).show();
        }
        else {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }
    public void add_card() {
        MainActivity.preferencesManager.saveObjectList(MainActivity.flashcardsManager.getDecks());
        Intent intent = new Intent(this, CreateFlashcard.class);
        startActivity(intent);
    }
    private void updateRandomButtonState() {
        if (deckSize <= 1) {
            random.setEnabled(false);
            random.setAlpha(0.5f);
        } else {
            random.setEnabled(true);
            random.setAlpha(1f);
        }
    }
}
