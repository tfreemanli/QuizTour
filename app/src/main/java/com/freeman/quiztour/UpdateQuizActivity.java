package com.freeman.quiztour;

import android.app.DatePickerDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateQuizActivity extends AppCompatActivity {
    EditText et_name;
    TextView tv_category, tv_difficulty,tv_startdate, tv_enddate;
    Button btn_start_datepick, btn_end_datepick;
    Button btn_delete, btn_update, btn_cancel;
    Quiz currQuiz;
    ArrayList<Question> questions;
    RecyclerView rv;
    CreateQuizRVAdapter adapter;
    final String TAG = "QuizTour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currQuiz = Config.getInstance().getQuiz();
        et_name = findViewById(R.id.et_updatequiz_name);
        et_name.setText(currQuiz.getName());
        tv_category = findViewById(R.id.tv_updatequiz_category);
        tv_category.setText(currQuiz.getCategory());
        tv_difficulty = findViewById(R.id.tv_updatequiz_difficulty);
        tv_difficulty.setText(currQuiz.getDifficulty());

        btn_start_datepick = findViewById(R.id.btn_updatequiz_startdatepick);
        btn_end_datepick = findViewById(R.id.btn_updatequiz_enddatepick);
        tv_startdate = findViewById(R.id.tv_updatequiz_startdate);
        tv_startdate.setText(currQuiz.getStartdate());
        tv_enddate = findViewById(R.id.tv_updatequiz_enddate);
        tv_enddate.setText(currQuiz.getEnddate());
        btn_start_datepick.setOnClickListener(v-> showDatePicker(tv_startdate));
        btn_end_datepick.setOnClickListener(v->showDatePicker(tv_enddate));

        questions = currQuiz.getQuestions();
        rv = findViewById(R.id.rv_updatequiz_question);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CreateQuizRVAdapter(questions, this);
        rv.setAdapter(adapter);
    }

    public void update(View view){

        if(!validate()) {
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference quizRef = db.collection("Quiz").document(currQuiz.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", et_name.getText().toString());
        updates.put("startdate", tv_startdate.getText().toString());
        updates.put("enddate", tv_enddate.getText().toString());

        quizRef.update(updates)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Quiz updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Quiz updated", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Error updating fields", e);
                });

        cancel(view);
    }

    //when delete button clicked
    public void delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Quiz");
        builder.setMessage("Are you sure you want to delete this quiz?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            deleteQuiz(currQuiz.getId());
            cancel(view);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();

    }

    public void deleteQuiz(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Quiz").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Quiz deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Quiz NOT deleted", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Error deleting quiz", e);
                });
    }

    //When cancel button clicked
    public void cancel(View view){
        Intent i = new Intent(UpdateQuizActivity.this, AdminMainActivity.class);
        startActivity(i);
        finish();
    }


    private void showDatePicker(TextView tvDate) {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建日期选择器对话框
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (DatePicker view, int year1, int month1, int dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    tvDate.setText(selectedDate);
                },
                year, month, day
        );
        dialog.show();
    }

    private boolean validate() {
        if (et_name.getText().toString().isEmpty()) {
            et_name.setError("Name is required");
            return false;
        }
        String sStartdate = tv_startdate.getText().toString();
        if (sStartdate.isEmpty() || !isValidDate(sStartdate, "dd/MM/yyyy")) {
            tv_startdate.setError("Start date is not validated");
            return false;
        }
        String sEnddate = tv_enddate.getText().toString();
        if(sEnddate.isEmpty() || !isValidDate(sEnddate, "dd/MM/yyyy")) {
            tv_enddate.setError("End date is not validated");
            return false;
        }

        return true;
    }

    public static boolean isValidDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // 关闭宽松解析，严格判断
        try {
            Date date = sdf.parse(dateStr);
            return true; // 解析成功，说明是合法日期
        } catch (ParseException e) {
            return false; // 解析失败，说明不合法
        }
    }
}