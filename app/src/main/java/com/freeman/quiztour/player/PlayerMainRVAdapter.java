package com.freeman.quiztour.player;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freeman.quiztour.R;
import com.freeman.quiztour.common.Config;
import com.freeman.quiztour.common.Quiz;

import java.util.ArrayList;

public class PlayerMainRVAdapter extends RecyclerView.Adapter<PlayerMainRVAdapter.PlayerMainRVHolder> {
    ArrayList<Quiz> quizList;
    Context context;
    boolean isPlayable;

    public PlayerMainRVAdapter(ArrayList<Quiz> quizList, Context context, boolean isPlayable) {
        this.quizList = quizList;
        this.context = context;
        this.isPlayable = isPlayable;
    }

    @NonNull
    @Override
    public PlayerMainRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int act = R.layout.cardview_player_quiz;
        PlayerMainRVHolder holder;
        if(isPlayable) {
            act = R.layout.cardview_player_quiz_playable;
            View view = LayoutInflater.from(context).inflate(act, parent, false);
            holder = new PlayerMainRVHolderPlayable(view);
        } else {
            View view = LayoutInflater.from(context).inflate(act, parent, false);
            holder = new PlayerMainRVHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerMainRVHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.tv_quiz_name.setText(getNonHTMLString(quiz.getName()));
        holder.tv_category.setText(getNonHTMLString(quiz.getCategory()));
        holder.tv_difficulty.setText(quiz.getDifficulty());
        holder.tv_startdate.setText(quiz.getStartdate());
        holder.tv_enddate.setText(quiz.getEnddate());

        if(isPlayable) {
            ((PlayerMainRVHolderPlayable)holder).btn_player_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Config.getInstance().setQuiz(quiz);
                    Intent intent = new Intent(context, PlayerPlayQuizActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }


    public class PlayerMainRVHolder extends RecyclerView.ViewHolder {
        TextView tv_quiz_name, tv_category, tv_difficulty, tv_startdate, tv_enddate;

        public PlayerMainRVHolder(@NonNull View itemView) {
            super(itemView);
            tv_quiz_name = itemView.findViewById(R.id.tv_quiz_name);
            tv_category = itemView.findViewById(R.id.tv_category);
            tv_difficulty = itemView.findViewById(R.id.tv_difficulty);
            tv_startdate = itemView.findViewById(R.id.tv_startdate);
            tv_enddate = itemView.findViewById(R.id.tv_enddate);

        }
    }

    public class PlayerMainRVHolderPlayable extends PlayerMainRVHolder {
        Button btn_player_start;
        public PlayerMainRVHolderPlayable(@NonNull View itemView) {
            super(itemView);
            btn_player_start = itemView.findViewById(R.id.btn_player_start);
        }
    }

    private String getNonHTMLString(String htmlString) {
        Spanned spanned = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY);
        return spanned.toString();
    }
}
