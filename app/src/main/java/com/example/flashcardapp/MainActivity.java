package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    RelativeLayout container;
    FlashcardDeck new_deck;
    FlashcardDeck deckToRemove = new FlashcardDeck();

    static FlashcardsManager flashcardsManager = new FlashcardsManager();
    static SharedPreferencesManager preferencesManager;
    Button createCategoryButton, showCategoryButton;
    TextInputLayout textInputLayoutDeckName;
    Animation slide_up, slide_down, hide;
    ArrayAdapter<FlashcardDeck> adapter;
    List<FlashcardDeck> deckList;
    ListView listView;
    ImageView appName, doneLottieAnimationView, deleteLottieAnimationView, splashImg;
    boolean flag = true;

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

        container = findViewById(R.id.relative_layout);
        showCategoryButton = findViewById(R.id.button_show);
        createCategoryButton = findViewById(R.id.createButton);
        textInputLayoutDeckName = findViewById(R.id.input_layout_deckname);
        listView = findViewById(R.id.flashcardListView);
        appName = findViewById(R.id.title);
        splashImg = findViewById(R.id.bg);
        doneLottieAnimationView = findViewById(R.id.doneLottie);
        deleteLottieAnimationView = findViewById(R.id.deleteLottie);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        hide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.hide);


        // Set initial translation values to position views outside of the visible area
        splashImg.setTranslationY(-3000);
        appName.setTranslationY(2000);
        doneLottieAnimationView.setTranslationX(2000);
        deleteLottieAnimationView.setTranslationY(2000);
        createCategoryButton.setTranslationX(-2000);
        listView.setTranslationX(2000);


        splashImg.animate().translationY(0).setDuration(1000).setStartDelay(0);
        appName.animate().translationY(0).setDuration(1000).setStartDelay(0);
        createCategoryButton.animate().translationX(0).setDuration(1000).setStartDelay(500);
        listView.animate().translationX(0).setDuration(1000).setStartDelay(500);
        deleteLottieAnimationView.animate().translationY(0).setDuration(1000).setStartDelay(500);


        // Set adapter
        adapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);
        listView.setAdapter(adapter);
        init();

        createCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call function
                createCategory();
            }
        });
        showCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call function
                showButton();
            }
        });
        doneLottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call function
                doneDeleting();
            }
        });
        deleteLottieAnimationView.setOnClickListener(new View.OnClickListener()  {
            // call function
            @Override
            public void onClick(View view) {
                // call function
                deleteDeck();
            }
        });
        Log.d("lifecycle","onCreate invoked");
    }

    public void init() {
        textInputLayoutDeckName.setVisibility(View.INVISIBLE);

        // Check the type of LayoutParams used by the container
        ViewGroup.LayoutParams layoutParams = container.getLayoutParams();

        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relativeLayoutParams = (RelativeLayout.LayoutParams) layoutParams;
            relativeLayoutParams.height = 150;
            container.setLayoutParams(relativeLayoutParams);
        } else if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams constraintLayoutParams = (ConstraintLayout.LayoutParams) layoutParams;
            constraintLayoutParams.height = 150;
            container.setLayoutParams(constraintLayoutParams);
        }
        container.startAnimation(slide_up);
    }
    public void createCategory() {
        String deckName = Objects.requireNonNull(textInputLayoutDeckName.getEditText()).getText().toString();

        if (!deckName.isEmpty()) {
            new_deck = new FlashcardDeck();
            new_deck.setDeckName(deckName);
            flashcardsManager.addDeck(new_deck);
            flashcardsManager.setActiveDeck(new_deck);
            preferencesManager.saveObjectList(flashcardsManager.getDecks());

            container.startAnimation(slide_down);
            Intent intent = new Intent(this, CreateFlashcard.class);
            startActivity(intent);
            Toast.makeText(this, "Created Deck: " + new_deck.toString(), Toast.LENGTH_SHORT).show();

        } else {
            // Handle the case where the input is empty
            Toast.makeText(this, "Please enter a deck name", Toast.LENGTH_SHORT).show();
        }
    }
    public void showButton() {
        if (flag) {
            textInputLayoutDeckName.setVisibility(View.VISIBLE);

            // Check the type of LayoutParams used by the container
            ViewGroup.LayoutParams layoutParams = container.getLayoutParams();

            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams relativeLayoutParams = (RelativeLayout.LayoutParams) layoutParams;
                relativeLayoutParams.height = 900;
                container.setLayoutParams(relativeLayoutParams);
            } else if (layoutParams instanceof ConstraintLayout.LayoutParams) {
                ConstraintLayout.LayoutParams constraintLayoutParams = (ConstraintLayout.LayoutParams) layoutParams;
                constraintLayoutParams.height = 900;
                container.setLayoutParams(constraintLayoutParams);
            }
            container.startAnimation(slide_up);
            flag = false;
        } else {
            flag = true;
            init();
        }
    }
    public void deleteDeck() {
        if (deckList == null || deckList.isEmpty()) {
            Toast.makeText(this, "No Decks To Delete", Toast.LENGTH_SHORT).show();
        }
        else {
            container.startAnimation(hide);
            deleteLottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(500);
            doneLottieAnimationView.animate().translationX(0).setDuration(1000).setStartDelay(500);


            ArrayAdapter<FlashcardDeck> slideAdapter = new ArrayAdapter<>(this, R.layout.list_view, deckList);
            listView.setAdapter(slideAdapter);
            listView.setOnTouchListener(new SlideListAnimatorController(this, listView) {
                public void onSwipeLeft(int pos) {
                    // Handle the swipe action here
                    deckToRemove = slideAdapter.getItem(pos);
                    slideAdapter.remove(deckToRemove);
                    MainActivity.flashcardsManager.removeDeck(deckToRemove); // Update your data source as well
                    MainActivity.preferencesManager.saveObjectList(deckList); // Save the updated list
                    slideAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    public void doneDeleting() {
        runOnUiThread(() -> {
            deleteLottieAnimationView.animate().translationY(0).setDuration(1000).setStartDelay(500);
            doneLottieAnimationView.animate().translationX(2000).setDuration(1000).setStartDelay(500);

            // Set the listView adapter back to the original adapter
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

        });
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
        onResume();
        Log.d("lifecycle","onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        onRestart();
        Log.d("lifecycle","onStop invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onStart();
        Log.d("lifecycle","onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }
}
