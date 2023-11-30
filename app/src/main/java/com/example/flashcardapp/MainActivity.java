package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static FlashcardsManager flashcardsManager = new FlashcardsManager();
    static SharedPreferencesManager preferencesManager;
    Button createCategoryButton;
    ArrayAdapter<FlashcardDeck> adapter;
    List<FlashcardDeck> deckList;
    ListView listView;
    Toolbar toolbar;
    //lastElement = Iterables.getLast(iterableList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve saved shared preference
        preferencesManager = new SharedPreferencesManager(this);
        deckList = preferencesManager.getObjectList();

        if (deckList == null) {
            deckList = new ArrayList<>(); // Initialize an empty list if null
        }
        flashcardsManager.setDecks(deckList);

        // Set toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set the title of the Toolbar
        getSupportActionBar().setTitle("Home");

        // Set adapter
        adapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);

        // ListView
        listView = findViewById(R.id.flashcardListView);
        listView.setAdapter(adapter);


        // Button
        createCategoryButton = findViewById(R.id.flashcardButton);
        createCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call function
                createCategory();
            }
        });
        Log.d("lifecycle","onCreate invoked");
    }

    // Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("lifecycle","onCreationOptionsMenu invoked");
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Swipe To Delete", Toast.LENGTH_SHORT).show();
        int itemId = item.getItemId();

        if (itemId == R.id.delete_item) {
            deleteFlashcardDeck();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        deckList = preferencesManager.getObjectList();

        // retrieve saved shared preference
        if (deckList == null) {
            deckList = new ArrayList<>(); // Initialize an empty list if null
        }
        // Set adapter
        adapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);

        // ListView
        listView = findViewById(R.id.flashcardListView);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("lifecycle","onStart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                // set active deck from the item clicked
                flashcardsManager.setActiveDeck(FlashcardsManager.decks.get(index));

                //get deck that it is being clicked on
                FlashcardDeck tempDeck = flashcardsManager.getActiveDeck();

                // create intent
                Intent intent;
                if (tempDeck.getFlashcards().isEmpty()) { //if list is empty add flashcards
                    Toast.makeText(MainActivity.this, "Empty List", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, CreateFlashcard.class);
                    startActivity(intent);
                }
                else {  //if list has cards play them
                    Toast.makeText(MainActivity.this, "Playing "+tempDeck.toString(), Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, PlayFlashcards.class);
                    startActivity(intent);
                }

            }
        });
        Log.d("lifecycle","onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }

    public void createCategory(){
        Toast.makeText(this, "Create Flashcard Deck", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CreateCategory.class);
        startActivity(intent);
    }
    public void deleteFlashcardDeck () {
        Intent intent = new Intent(this, DeleteFlashcardDeck.class);
        startActivity(intent);
    }
}
