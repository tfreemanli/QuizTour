package com.freeman.quiztour.player;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeman.quiztour.BaseActivity;
import com.freeman.quiztour.R;
import com.freeman.quiztour.common.Config;
import com.freeman.quiztour.common.Quiz;
import com.freeman.quiztour.common.Rate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayerMainActivity extends BaseActivity {
    private static final String TAG = "PlayerMainActivity";
    Spinner spn_player_quizfilter;
    FirebaseFirestore db;
    ProgressBar pb;
    ArrayList<Quiz> allQuiz;
    ArrayList<Quiz> onGoingQuiz; //only playable
    ArrayList<Quiz> upcomingQuiz;
    ArrayList<Quiz> pastQuiz;
    ArrayList<Quiz> participatedQuiz;
    RecyclerView rv_player_quizlist;
    PlayerMainRVAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupProfile();

        //Get current user and email
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }

        spn_player_quizfilter = findViewById(R.id.spn_player_quizfilter);
        Config.init_filter_spinner(spn_player_quizfilter, R.array.player_quiz_filter, this);
        configSpinner();

        pb = findViewById(R.id.progressBar);
        rv_player_quizlist = findViewById(R.id.rv_player_quizlist);
        rv_player_quizlist.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        db.collection("Quiz")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allQuiz = new ArrayList<>();
                        onGoingQuiz = new ArrayList<>();
                        upcomingQuiz = new ArrayList<>();
                        pastQuiz = new ArrayList<>();
                        participatedQuiz = new ArrayList<>();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date currentDate = new Date();
                        Outerloop:
                        for (var document : task.getResult()) {
                            Quiz quiz = document.toObject(Quiz.class);
                            allQuiz.add(quiz);
                            //check if it is participated
                            ArrayList<Rate> rates = quiz.getRates();
                            for (Rate rate : rates) {
                                String email = rate.getEmail();
                                if (email != null && email.toLowerCase().equals(currentUser.getEmail().toLowerCase())) {
                                    participatedQuiz.add(quiz);
                                    continue Outerloop;
                                }
                            }

                            //check the date and divide into three categories
                            try {
                                Date startDate = sdf.parse(quiz.getStartdate());
                                Date endDate = sdf.parse(quiz.getEnddate());

                                if (currentDate.before(startDate)) {
                                    upcomingQuiz.add(quiz);
                                } else if (currentDate.after(endDate)) {
                                    pastQuiz.add(quiz);
                                } else {
                                    onGoingQuiz.add(quiz);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        adapter = new PlayerMainRVAdapter(onGoingQuiz, this, true);
                        rv_player_quizlist.setAdapter(adapter);
                        pb.setVisibility(View.GONE);
                        rv_player_quizlist.setVisibility(View.VISIBLE);


                    } else {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(this, "Error in loading Quiz.", Toast.LENGTH_SHORT).show();

                    }

                });

    }

    private void configSpinner() {
        spn_player_quizfilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = spn_player_quizfilter.getSelectedItem().toString().toLowerCase();
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                switch (filter) {
                    case "upcoming":
                        adapter = new PlayerMainRVAdapter(upcomingQuiz, PlayerMainActivity.this, false);
                        break;
                    case "past":
                        adapter = new PlayerMainRVAdapter(pastQuiz, PlayerMainActivity.this, false);
                        break;
                    case "participated":
                        adapter = new PlayerMainRVAdapter(participatedQuiz, PlayerMainActivity.this, false);
                        break;
                    default:
                        adapter = new PlayerMainRVAdapter(onGoingQuiz, PlayerMainActivity.this, true);
                        break;
                }
                rv_player_quizlist.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}