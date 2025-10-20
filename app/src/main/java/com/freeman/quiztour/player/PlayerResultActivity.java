package com.freeman.quiztour.player;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.freeman.quiztour.R;

public class PlayerResultActivity extends AppCompatActivity {
    TextView tv_score;
    Button btn_save;
    int score;

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

        tv_score = findViewById(R.id.tv_score);
        btn_save = findViewById(R.id.btn_save);
        score = getIntent().getIntExtra("score", 0);
        tv_score.setText(String.valueOf(score));

        btn_save.setOnClickListener(v -> {
            //TODO save score to database
            finish();
        });
    }
}