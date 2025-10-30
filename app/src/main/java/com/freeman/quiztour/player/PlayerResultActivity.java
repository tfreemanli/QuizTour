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
import com.freeman.quiztour.common.Quiz;
import com.freeman.quiztour.common.Rate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PlayerResultActivity extends BaseActivity {
    TextView tv_score;
    Button btn_save;
    int score;
    Quiz quiz;
    RadioGroup rg_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_result);
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

        tv_score = findViewById(R.id.tv_score);
        btn_save = findViewById(R.id.btn_save);
        score = getIntent().getIntExtra("score", 0);
        tv_score.setText(String.valueOf(score));
        rg_rate = findViewById(R.id.rg_rate);

        quiz = Config.getInstance().getQuiz();


        btn_save.setOnClickListener(v -> {
            String email = currentUser.getEmail();
            if (email == null) {
                Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_SHORT).show();
                return;
            }
            //Get the rate
            int selectID = rg_rate.getCheckedRadioButtonId();
            if (selectID == -1) {
                Toast.makeText(getApplicationContext(), "Please select a rate", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton rb = findViewById(selectID);
            int iRate = Integer.parseInt(rb.getText().toString());

            //Create a Rate object and add it to the Rate List
            ArrayList<Rate> newRates = quiz.getRates();
            newRates.add(new Rate(iRate, email ,score));

            //Save the newRates to the document
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Quiz").document(quiz.getId()).update("rates", newRates).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Saved. Thank you for playing.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PlayerResultActivity.this, PlayerMainActivity.class);
                startActivity(i);
            }).addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            });

        });
    }
}