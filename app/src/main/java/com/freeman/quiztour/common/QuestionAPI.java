package com.freeman.quiztour.common;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionAPI {
    @GET("api.php")
    Call<QuestionResponse> getQuestions(
            @Query("amount") int amount,
            @Query("difficulty") String difficulty,
            @Query("category") int category
    );

}
