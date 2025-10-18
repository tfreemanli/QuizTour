package com.freeman.quiztour.common;

import java.util.ArrayList;

public interface QuestionCallback {
    void onSuccess(ArrayList<Question> questions);
    void onError(String err);
}
