package com.freeman.quiztour.common;

public class Rate {
    private int rate;
    private String email;
    private int score;

    public Rate(int rate, String email,  int score) {
        this.rate = rate;
        this.email = email;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }


    public Rate(){}

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
