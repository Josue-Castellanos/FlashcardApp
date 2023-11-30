package com.example.flashcardapp;

public class Flashcard {
    private String answer;
    private String question;

    public Flashcard(String answer, String question) {
        this.question= question;
        this.answer= answer;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
