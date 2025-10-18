package com.freeman.quiztour.common;

import java.util.ArrayList;

public class Quiz {
    private String id, name, category, difficulty,startdate, enddate;
    private ArrayList<Question> questions;
    private ArrayList<Rate> rates;

    public Quiz() {
    }

    public Quiz(String id, String name, String category, String difficulty, String startdate, String enddate, ArrayList<Rate> rate, ArrayList<Question> questions) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.startdate = startdate;
        this.enddate = enddate;
        this.rates = rate;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }

    public void setRates(ArrayList<Rate> rate) {
        this.rates = rate;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
