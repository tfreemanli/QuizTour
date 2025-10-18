package com.freeman.quiztour.admin;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.freeman.quiztour.common.Config;
import com.freeman.quiztour.common.Quiz;
import com.freeman.quiztour.R;

import java.util.ArrayList;

public class AdminMainRVAdapter extends RecyclerView.Adapter<AdminMainRVAdapter.AdminMainRVHolder> {
    ArrayList<Quiz> quizzes;
    Context context;

    public AdminMainRVAdapter(Context context, ArrayList<Quiz> quizzes){
        this.quizzes = quizzes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminMainRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_quiz, parent, false);
        return new AdminMainRVHolder(view);
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdminMainRVHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        holder.tv_name.setText(getNonHTMLString(quiz.getName()));
        holder.tv_category.setText(getNonHTMLString(quiz.getCategory()));
        holder.tv_difficulty.setText(quiz.getDifficulty());
        holder.tv_startdate.setText(quiz.getStartdate());
        holder.tv_enddate.setText(quiz.getEnddate());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.getInstance().setQuiz(quiz);
                Intent i = new Intent(context, UpdateQuizActivity.class);
                context.startActivity(i);
            }
        });
    }

    public static class AdminMainRVHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView tv_name, tv_category, tv_difficulty, tv_startdate, tv_enddate;

        public AdminMainRVHolder(@NonNull final View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.cardview_quiz);
            tv_name = itemView.findViewById(R.id.tv_quiz_name);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_difficulty = itemView.findViewById(R.id.tv_difficulty);
            tv_startdate = itemView.findViewById(R.id.tv_startdate);
            tv_enddate = itemView.findViewById(R.id.tv_enddate);
        }

    }

    private String getNonHTMLString(String htmlString) {
        Spanned spanned = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY);
        return spanned.toString();
    }
}
