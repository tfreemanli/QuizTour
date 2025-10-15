package com.freeman.quiztour;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateQuizActivity extends AppCompatActivity {
    EditText et_name;
    Spinner spn_category, spn_difficulty;
    Button btn_start_datepick, btn_end_datepick;
    TextView tv_startdate, tv_enddate;
    RecyclerView rv;
    CreateQuizRVAdapter adapter;

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
}