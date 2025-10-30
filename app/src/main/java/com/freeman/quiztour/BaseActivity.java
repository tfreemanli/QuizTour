package com.freeman.quiztour;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity  extends AppCompatActivity {

    protected  FirebaseAuth mAuth;
    protected FirebaseUser currentUser;

    public void setupProfile() {
        TextView tv_name = findViewById(R.id.tv_banner_username);
        TextView tv_logout = findViewById(R.id.tv_banner_logout);
        if (tv_name == null || tv_logout == null) return;

        if (currentUser == null) {
            return;
        }
        tv_name.setText(currentUser.getEmail());
        tv_logout.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        mAuth.signOut();
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                    })
                    .setNegativeButton("No", (dialog1, which) -> {
                        dialog1.dismiss();
                    })
                    .show();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //setupProfile();
    }


}
