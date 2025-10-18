package com.freeman.quiztour.admin;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.freeman.quiztour.common.Question;
import com.freeman.quiztour.R;

import java.util.ArrayList;

public class CreateQuizRVAdapter extends RecyclerView.Adapter<CreateQuizRVAdapter.CreateQuizRVHolder> {
    ArrayList<Question> questions;
    Context context;

    public CreateQuizRVAdapter(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public CreateQuizRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_question, parent, false);
        return new CreateQuizRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateQuizRVHolder holder, int position) {
        Question question = questions.get(position);
        holder.tv_question.setText(getNonHTMLString(question.getQuestion()));
        holder.tv_type.setText(question.getType());
        holder.tv_category.setText(getNonHTMLString(question.getCategory()));
        holder.tv_difficulty.setText(question.getDifficulty());
        holder.tv_correctanswer.setText(getNonHTMLString(question.getCorrect_answer()));
        String temp = "";
        ArrayList<String> alIncorrect = question.getIncorrect_answers();
        if(alIncorrect!=null){
            for(String str: alIncorrect){
                temp += (getNonHTMLString(str) + " | ");
            }
        }
        holder.tv_incorrectanswer.setText(temp);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class CreateQuizRVHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView tv_question, tv_category, tv_difficulty, tv_type, tv_correctanswer, tv_incorrectanswer;
        public CreateQuizRVHolder(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.cardview_question);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_difficulty = itemView.findViewById(R.id.tv_difficulty);
            tv_correctanswer = itemView.findViewById(R.id.tv_correctanswer);
            tv_incorrectanswer = itemView.findViewById(R.id.tv_incorrectanswer);
        }
    }

    private String getNonHTMLString(String htmlString) {
        Spanned spanned = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY);
        return spanned.toString();
    }

}
