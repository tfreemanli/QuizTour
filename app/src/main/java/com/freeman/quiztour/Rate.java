package com.freeman.quiztour;

public class Rate {
    private int rate;
    private String email;

    public Rate(int rate, String email) {
        this.rate = rate;
        this.email = email;
    }

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
