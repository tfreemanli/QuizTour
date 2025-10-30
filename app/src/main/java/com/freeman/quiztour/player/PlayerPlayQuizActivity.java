package com.freeman.quiztour.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.freeman.quiztour.BaseActivity;
import com.freeman.quiztour.R;
import com.freeman.quiztour.common.Config;
import com.freeman.quiztour.common.Question;
import com.freeman.quiztour.common.Quiz;

import java.util.ArrayList;

public class PlayerPlayQuizActivity extends BaseActivity {
    Quiz currQuiz;
    TextView tv_timer, tv_score, tv_questionnumber, tv_question;
    RadioGroup rg_options;
    Button btn_next;
    ArrayList<Question> al_question;
    int currentIndex=0; //QuizNumber
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_play_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupProfile();

        tv_timer = findViewById(R.id.tv_timer);
        tv_score = findViewById(R.id.tv_score);
        tv_questionnumber = findViewById(R.id.tv_questionnumber);
        tv_question = findViewById(R.id.tv_question);
        rg_options = findViewById(R.id.rg_options);
        btn_next = findViewById(R.id.btn_play_next);

        initQuestions();
        showQuestions();

        btn_next.setOnClickListener(v -> checkAnswer());

    }

    public void checkAnswer() {
        int selectedId = rg_options.getCheckedRadioButtonId();
        if(selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        String sAnswer = rb.getText().toString();

        Question q = al_question.get(currentIndex);
        if(sAnswer.equals(q.getCorrect_answer())) {
            score++;
            Toast.makeText(this, "Hooray! It's Correct.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Oops! Try the next one.", Toast.LENGTH_SHORT).show();
        }

        currentIndex++;
        showQuestions();
    }

    public void initQuestions(){

        currQuiz = Config.getInstance().getQuiz();
        if(currQuiz == null) {
            Toast.makeText(this, "Quiz not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        al_question = currQuiz.getQuestions();
        if(al_question == null || al_question.size() == 0) {
            Toast.makeText(this, "No questions found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //TODO save quiz as participated.
    }
    public void showQuestions(){
        if (currentIndex >= al_question.size()) {
            goToResult();
            return;
        }

        Question q = al_question.get(currentIndex);
        //题目
        tv_question.setText(q.getQuestion());

        //选项
        rg_options.removeAllViews();
        //get a random position for the correct answer, from 0 to 3, or 0 to 1.
        int rand = (int) (Math.random() * q.getIncorrect_answers().size());

        for (int i=0;i<q.getIncorrect_answers().size();i++) {
            //firstly, insert the correct answer into the rg.
            if(i == rand) {
                RadioButton rb = new RadioButton(this);
                rb.setText(q.getCorrect_answer());
                rg_options.addView(rb);
            }

            RadioButton rb = new RadioButton(this);
            rb.setText(q.getIncorrect_answers().get(i));
            rg_options.addView(rb);
        }

        rg_options.clearCheck();
        tv_score.setText("" + score);
        tv_questionnumber.setText("" + (currentIndex + 1));
    }

    public void goToResult() {
        Intent intent = new Intent(this, PlayerResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);

    }
}