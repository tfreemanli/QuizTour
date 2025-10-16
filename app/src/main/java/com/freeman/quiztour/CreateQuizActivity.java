package com.freeman.quiztour;

import android.app.DatePickerDialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateQuizActivity extends AppCompatActivity implements QuestionCallback{
    EditText et_name;
    Spinner spn_category, spn_difficulty;
    Button btn_start_datepick, btn_end_datepick;
    Button btn_reload, btn_save, btn_cancel;
    TextView tv_startdate, tv_enddate;
    ArrayList<Question> questions;
    RecyclerView rv;
    CreateQuizRVAdapter adapter;
    final String TAG = "QuizTour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_createquiz_name);

        spn_category = findViewById(R.id.spn_createquiz_category);
        Config.init_category_spinner(spn_category, this);
        spn_difficulty = findViewById(R.id.spn_createquiz_difficulty);
        Config.init_difficulty_spinner(spn_difficulty, this);

        btn_start_datepick = findViewById(R.id.btn_start_datepick);
        btn_end_datepick = findViewById(R.id.btn_end_datepick);
        tv_startdate=findViewById(R.id.tv_startdate);
        tv_enddate = findViewById(R.id.tv_enddate);
        btn_start_datepick.setOnClickListener(v-> showDatePicker(tv_startdate));
        btn_end_datepick.setOnClickListener(v->showDatePicker(tv_enddate));

        btn_reload = findViewById(R.id.btn_createquiz_reload);
        btn_save = findViewById(R.id.btn_createquiz_save);
        btn_cancel = findViewById(R.id.btn_createquiz_cancel);

        rv = findViewById(R.id.rv_question);
        rv.setLayoutManager(new LinearLayoutManager(this));
        questions = new ArrayList<>();
        adapter = new CreateQuizRVAdapter(questions, this);
        rv.setAdapter(adapter);

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
        if(questions == null || questions.size() == 0) {
            Toast.makeText(this, "Reload questions first please.", Toast.LENGTH_SHORT).show();
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


    public void reload(View view) {
        QuestionController api = new QuestionController(this);
        CategoryItem curCate = (CategoryItem)spn_category.getSelectedItem();
        String curDifficulty = spn_difficulty.getSelectedItem().toString();
        if(curDifficulty.toLowerCase().contains("any difficulty")){ curDifficulty = null; }
        api.start(curDifficulty, curCate.getId(), this);
    }


    public void save(View view) {
        if(!validate()) {
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Quiz newquiz = new Quiz();
        newquiz.setName(et_name.getText().toString());
        newquiz.setCategory(spn_category.getSelectedItem().toString());
        newquiz.setDifficulty(spn_difficulty.getSelectedItem().toString());
        newquiz.setStartdate(tv_startdate.getText().toString());
        newquiz.setEnddate(tv_enddate.getText().toString());
        ArrayList<Rate> rates = new ArrayList<Rate>();
        rates.add(new Rate(5,"sample@email.com"));
        newquiz.setRates(rates);
        newquiz.setQuestions(questions);
        // Add a new document with a generated ID
        db.collection("Quiz")
                .add(newquiz)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Quiz added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Quiz added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "Failed in adding Quiz.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onSuccess(ArrayList<Question> arr) {
        if(arr!= null){
            questions.clear();
            questions.addAll(arr);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String err) {

    }
}