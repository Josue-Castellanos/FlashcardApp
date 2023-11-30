package com.example.flashcardapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesManager {

    private static final String PREF_KEY = "decks";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveObjectList(List<FlashcardDeck> objectList) {
        String json = gson.toJson(objectList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, json);
        editor.apply();
    }

    public List<FlashcardDeck> getObjectList() {
        String json = sharedPreferences.getString(PREF_KEY, null);

        Type type = new TypeToken<List<FlashcardDeck>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
