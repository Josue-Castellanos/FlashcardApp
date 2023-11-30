package com.example.flashcardapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlashcardsManager {
    static Flashcard curretFlashcard;
    // list of decks created
    static List<FlashcardDeck> decks;
    // the deck that is being played or modified
    static FlashcardDeck activeDeck;
    // the position of the active deck in the list
    private static int currentIndex;

    public FlashcardsManager() {
        decks = new ArrayList<>();
        currentIndex = 0;
    }
    public void setActiveDeck(FlashcardDeck deck) {
        activeDeck = deck;
        currentIndex = 0; // Reset the current index when changing decks
    }

    public void addDeck(FlashcardDeck deck) {
        decks.add(deck);
    }
    public void removeDeck(FlashcardDeck deck) {
        decks.remove(deck);
    }

    public void setDecks(List<FlashcardDeck> deckList) {
        decks = deckList;
    }


    public FlashcardDeck getActiveDeck() {
        return activeDeck;
    }
    public List<FlashcardDeck> getDecks() {
        return decks;
    }
    public void removeCurrentFlashcard() {
        if (activeDeck != null && !activeDeck.getFlashcards().isEmpty()) {
            activeDeck.getFlashcards().remove(currentIndex);
        }
    }
    public Flashcard getCurrentFlashcard() {
        if (activeDeck != null && !activeDeck.getFlashcards().isEmpty()) {
            return activeDeck.getFlashcards().get(currentIndex);
        }
        return null;
    }
    public Flashcard getNextFlashcard() {
        if (activeDeck != null && !activeDeck.getFlashcards().isEmpty()) {
            currentIndex = currentIndex + 1;
            return activeDeck.getFlashcards().get(currentIndex);
        }
        return null;
    }
    public Flashcard getPreviousFlashcard() {
        if (activeDeck != null && !activeDeck.getFlashcards().isEmpty()) {
            currentIndex = currentIndex - 1;
            return activeDeck.getFlashcards().get(currentIndex);
        }
        return null;
    }
    public Flashcard getRandomFlashcard() {
        if (activeDeck != null && !activeDeck.getFlashcards().isEmpty()) {
            Random random = new Random();
            currentIndex = random.nextInt(activeDeck.getFlashcards().size());
            return activeDeck.getFlashcards().get(currentIndex);
        }
        return null;
    }
    public int getCurrentFlashcardIndex() {
        return currentIndex;
    }
    public void setCurrentFlashcardAnswer(String answer) {
        activeDeck.getFlashcards().get(getCurrentFlashcardIndex()).setAnswer(answer);
    }
    public void setCurrentFlashcardQuestion(String question) {
        activeDeck.getFlashcards().get(getCurrentFlashcardIndex()).setQuestion(question);

    }
    public int getActiveDeckSize () {
        return activeDeck.getFlashcards().size();
    }
    public int restIndex() {
        if (currentIndex + 1 == activeDeck.getFlashcards().size()) {
            // If at the end of the list, reset to the beginning
            currentIndex = 0;
        } else if (currentIndex == 0) {
            // If at the beginning of the list, reset to the last element
            currentIndex = activeDeck.getFlashcards().size() - 1;
        }
        return currentIndex;
    }
}
