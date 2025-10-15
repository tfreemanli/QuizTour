package com.freeman.quiztour;

import java.util.ArrayList;

public class Question {
    private String type, category, difficulty, question, correct_answer;
    private ArrayList<String> incorrect_answer;

    public Question() {
    }

    public Question(String type, String category, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answer) {
        this.type = type;
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answer = incorrect_answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answer() {
        return incorrect_answer;
    }

    public void setIncorrect_answer(ArrayList<String> incorrect_answer) {
        this.incorrect_answer = incorrect_answer;
    }
}
