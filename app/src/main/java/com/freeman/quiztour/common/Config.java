package com.freeman.quiztour.common;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.freeman.quiztour.R;

import java.util.ArrayList;

public class Config {
    private static Config instance;
    private Quiz quiz;

    private Config() {}

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public static void init_difficulty_spinner(Spinner spn_difficulty, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.createquiz_difficulty,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_difficulty.setAdapter(adapter);
    }

    public static void init_filter_spinner(Spinner spn_quiz_filter, int arrayID, Context context){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                arrayID,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_quiz_filter.setAdapter(adapter);
    }

    public static void init_category_spinner(Spinner spn_category, Context context){
        //spn_category = findViewById(R.id.spn_createquiz_category);

        ArrayList<CategoryItem> categoryItems = new ArrayList<CategoryItem>();
        categoryItems.add(new CategoryItem(0,"Random Category"));
        categoryItems.add(new CategoryItem(9,"General Knowledge"));
        categoryItems.add(new CategoryItem(10,"Entertainment: Books"));
        categoryItems.add(new CategoryItem(11,"Entertainment: Film"));
        categoryItems.add(new CategoryItem(12,"Entertainment: Music"));
        categoryItems.add(new CategoryItem(13,"Entertainment: Musicals & Theatres"));
        categoryItems.add(new CategoryItem(14,"Entertainment: Television"));
        categoryItems.add(new CategoryItem(15,"Entertainment: Video Games"));
        categoryItems.add(new CategoryItem(16,"Entertainment: Board Games"));
        categoryItems.add(new CategoryItem(17,"Science & Nature"));
        categoryItems.add(new CategoryItem(18,"Science: Computers"));
        categoryItems.add(new CategoryItem(19,"Science: Mathematics"));
        categoryItems.add(new CategoryItem(20,"Mythology"));
        categoryItems.add(new CategoryItem(21,"Sports"));
        categoryItems.add(new CategoryItem(22,"Geography"));
        categoryItems.add(new CategoryItem(23,"History"));
        categoryItems.add(new CategoryItem(24,"Politics"));
        categoryItems.add(new CategoryItem(25,"Art"));
        categoryItems.add(new CategoryItem(26,"Celebrities"));
        categoryItems.add(new CategoryItem(27,"Animals"));
        categoryItems.add(new CategoryItem(28,"Vehicles"));
        categoryItems.add(new CategoryItem(29,"Entertainment: Comics"));
        categoryItems.add(new CategoryItem(30,"Science: Gadgets"));
        categoryItems.add(new CategoryItem(31,"Entertainment: Japanese Anime & Manga"));
        categoryItems.add(new CategoryItem(32,"Entertainment: Cartoon & Animations"));

        ArrayAdapter<CategoryItem> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                categoryItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setAdapter(adapter);
    }

}
