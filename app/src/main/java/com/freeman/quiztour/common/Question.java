package com.freeman.quiztour.common;

import android.text.Html;
import android.text.Spanned;

import java.util.ArrayList;

public class Question {
    private String type, category, difficulty, question, correct_answer;
    private ArrayList<String> incorrect_answers;

    public Question() {
    }

    public Question(String type, String category, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answers) {
        this.type = type;
        this.category = category;
        this.difficulty = difficulty;
        Spanned span = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY);
        this.question = span.toString();
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
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
        Spanned span = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY);
        this.question = span.toString();
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
