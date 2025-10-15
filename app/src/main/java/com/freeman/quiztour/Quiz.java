package com.freeman.quiztour;

import java.util.ArrayList;
import java.util.Date;

public class Quiz {
    private String name, category, difficulty;
    private Date startdate, enddate;
    private int rate;
    private ArrayList<Question> questions;

    public Quiz() {
    }

    public Quiz(String name, String category, String difficulty, Date startdate, Date enddate, int rate, ArrayList<Question> questions) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.startdate = startdate;
        this.enddate = enddate;
        this.rate = rate;
        this.questions = questions;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
