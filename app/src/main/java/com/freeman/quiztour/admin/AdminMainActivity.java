package com.freeman.quiztour.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.freeman.quiztour.common.Config;
import com.freeman.quiztour.common.Quiz;
import com.freeman.quiztour.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AdminMainActivity extends BaseActivity {
    Button btn_quiz_add;
    Spinner spn_quiz_filter;
    RecyclerView rv;
    ProgressBar pb;
    AdminMainRVAdapter adapter;
    ArrayList<Quiz> quizList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupProfile();

        btn_quiz_add = findViewById(R.id.btn_quiz_add);
        btn_quiz_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMainActivity.this, CreateQuizActivity.class);
                startActivity(i);
            }
        });

        spn_quiz_filter = findViewById(R.id.spn_quiz_filter);
        Config.init_filter_spinner(spn_quiz_filter, R.array.quiz_filter ,this);
        spn_quiz_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filter = spn_quiz_filter.getSelectedItem().toString().toLowerCase();
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                ArrayList<Quiz> filteredList = new ArrayList<>();

                switch (filter) {
                    case "all":
                        if(quizList!=null) Collections.sort(quizList, (q1, q2) -> q1.getName().compareTo(q2.getName()));
                        adapter = new AdminMainRVAdapter(AdminMainActivity.this, quizList);
                        rv.setAdapter(adapter);
                        break;
                    case "upcoming":
                        for (Quiz q : quizList) {
                            try {
                                if (sdf.parse(q.getStartdate()).after(today)) {
                                    filteredList.add(q);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(filteredList!=null) Collections.sort(filteredList, (q1, q2) -> q1.getName().compareTo(q2.getName()));
                        adapter = new AdminMainRVAdapter(AdminMainActivity.this, filteredList);
                        rv.setAdapter(adapter);
                        break;
                    case "ongoing":
                        for (Quiz q : quizList) {
                            try {
                                if (sdf.parse(q.getStartdate()).before(today) && sdf.parse(q.getEnddate()).after(today)) {
                                    filteredList.add(q);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(filteredList!=null) Collections.sort(filteredList, (q1, q2) -> q1.getName().compareTo(q2.getName()));
                        adapter = new AdminMainRVAdapter(AdminMainActivity.this, filteredList);
                        rv.setAdapter(adapter);
                        break;
                    case "past":
                        for (Quiz q : quizList) {
                            try {
                                if (sdf.parse(q.getEnddate()).before(today)) {
                                    filteredList.add(q);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(filteredList!=null) Collections.sort(filteredList, (q1, q2) -> q1.getName().compareTo(q2.getName()));
                        adapter = new AdminMainRVAdapter(AdminMainActivity.this, filteredList);
                        rv.setAdapter(adapter);
                        break;
                    default:
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        pb = findViewById(R.id.progressBar);
        rv = findViewById(R.id.rv_quiz);
        rv.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        db.collection("Quiz")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        quizList = new ArrayList<>();
                        for (int i = 0; i < task.getResult().size(); i++) {
                            Quiz quiz = task.getResult().getDocuments().get(i).toObject(Quiz.class);
                            quizList.add(quiz);
                        }
                        if(quizList!=null) Collections.sort(quizList, (q1, q2) -> q1.getName().compareTo(q2.getName()));
                        //quizList.sort(Comparator.comparing(Quiz::getName));
                        adapter = new AdminMainRVAdapter(AdminMainActivity.this, quizList);
                        rv.setAdapter(adapter);
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    } else {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(this, "Error in loading Quiz.", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}