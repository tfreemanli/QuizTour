package com.freeman.quiztour;

public class CategoryItem {
    private int id;
    private String text;

    public CategoryItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    @Override
    public String toString() {
        return text;
    }
}
