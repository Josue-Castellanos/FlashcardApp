package com.example.flashcardapp;

import java.util.ArrayList;
import java.util.List;

public class FlashcardDeck {
    // Flashcard deck title
    private String deckName;

    // list of flashcards
    private List<Flashcard> flashcards;

    // constructor, initialize a new flashcard list
    public FlashcardDeck() {
        flashcards = new ArrayList<>();
    }

    // add card to list
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    // delete card form list
    public void removeFlashcard(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }

    // get list of flashcards
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    // Override toString method to return the deck name
    @Override
    public String toString() {
        return deckName;
    }

    // set the name of deck
    public void setDeckName(String name) {
        deckName = name;
    }

}
