package com.example.flashcardapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditFlashcard extends AppCompatActivity {
    String questionText;
    String answerText;
    EditText qText, aText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        qText = findViewById(R.id.questionText);
        aText = findViewById(R.id.answerText);

        questionText = MainActivity.flashcardsManager.getCurrentFlashcard().getQuestion();
        answerText = MainActivity.flashcardsManager.getCurrentFlashcard().getAnswer();

        qText.setText(questionText);
        aText.setText(answerText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Add button listener
        Button save = findViewById(R.id.saveButton);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEditing();
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
    public void saveEditing() {
        // pull question and answer from EditText box
        EditText questionEditText = findViewById(R.id.questionText);
        EditText answerEditText = findViewById(R.id.answerText);
        String question = questionEditText.getText().toString();
        String answer = answerEditText.getText().toString();

        // create flashcard, add new card to the flashcard list
        MainActivity.flashcardsManager.setCurrentFlashcardAnswer(answer);
        MainActivity.flashcardsManager.setCurrentFlashcardQuestion(question);
        setResult(RESULT_OK);

        // save changes
        MainActivity.preferencesManager.saveObjectList(MainActivity.flashcardsManager.getDecks());
        // clear the TextView boxes to add another card
        ((EditText) findViewById(R.id.questionText)).setText("");
        ((EditText) findViewById(R.id.answerText)).setText("");
        finish();
    }

}
