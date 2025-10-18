package com.freeman.quiztour.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionController implements Callback<QuestionResponse>{
    final String BASE_URL = "https://opentdb.com/";
    public QuestionResponse questionResponse;
    public ArrayList<Question> all;
    Context context;
    QuestionCallback callback;


    public QuestionController(Context context) {
        this.context = context;
    }

    public void start(String difficulty, int category, QuestionCallback callback) {

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
        QuestionAPI questionAPI = retrofit.create(QuestionAPI.class);
        Call<QuestionResponse> call = questionAPI.getQuestions(10, difficulty, category);
        call.enqueue(this);
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
        if(response.isSuccessful()){
            questionResponse = response.body();
            all = questionResponse.getResults();
            if(all !=null){
                //DataHolder.getInstance().setDataList(all);
                //TODO update RV asynchronusly
                callback.onSuccess(all);

                Log.d("Question_API", "Question:"+ all.size());
                Toast.makeText(context, "Successfully response JSON Question:"+ all.size(), Toast.LENGTH_SHORT).show();
            } else{
                Log.d("Question_API","Error Question's list is empty");
                Toast.makeText(context, "Error! getting 0 Question.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("Question_API","Error getting Question");
        }

    }

    @Override
    public void onFailure(Call<QuestionResponse> call, Throwable t) {
        Toast.makeText(context, "Error getting API.", Toast.LENGTH_SHORT).show();
    }
}
