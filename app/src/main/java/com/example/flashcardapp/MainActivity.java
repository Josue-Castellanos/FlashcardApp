package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView list, appName, lottieAnimationView, splashImg;

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

        listView = findViewById(R.id.flashcardListView);
        createCategoryButton = findViewById(R.id.flashcardButton);
        appName = findViewById(R.id.title);
        splashImg = findViewById(R.id.bg);
        lottieAnimationView = findViewById(R.id.editLottie);

        // Set initial translation values to position views outside of the visible area
        splashImg.setTranslationY(-3000);
        appName.setTranslationY(2000);
        lottieAnimationView.setTranslationY(2000);
        createCategoryButton.setTranslationX(-2000);
        listView.setTranslationX(2000);

        splashImg.animate().translationY(0).setDuration(1000).setStartDelay(0);
        appName.animate().translationY(0).setDuration(1000).setStartDelay(0);
        createCategoryButton.animate().translationX(0).setDuration(1000).setStartDelay(500);
        listView.animate().translationX(0).setDuration(1000).setStartDelay(500);
        lottieAnimationView.animate().translationY(0).setDuration(1000).setStartDelay(500);

        // Set adapter
        adapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);

        listView.setAdapter(adapter);


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
        int itemId = item.getItemId();

        if (itemId == R.id.delete_item) {
            if (deckList == null || deckList.isEmpty()) {
                Toast.makeText(this, "No Decks To Delete", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Swipe To Delete", Toast.LENGTH_SHORT).show();
                deleteFlashcardDeck();
            }
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
