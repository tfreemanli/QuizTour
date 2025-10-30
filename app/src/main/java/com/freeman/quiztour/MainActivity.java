package com.freeman.quiztour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.freeman.quiztour.admin.AdminMainActivity;
import com.freeman.quiztour.player.PlayerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {
    Button btn_admin, btn_player;
    EditText et_email, et_password;
    Button btn_login, btn_register;

    //private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupProfile();

        //mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_login.setOnClickListener(v->{
            login();
        });
        btn_register.setOnClickListener(v->{
            register();
        });

        btn_admin = findViewById(R.id.btn_main_admin);
        btn_player = findViewById(R.id.btn_main_player);
        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setText("admin@email.com");
                et_password.setText("12345678");

            }
        });
        btn_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_email.setText("user@email.com");
                et_password.setText("12345678");
            }
        });
    }

    public void login() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email and password cannot be empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser aUser = mAuth.getCurrentUser();
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            if (email.equals("admin@email.com")) {
                                Intent i = new Intent(MainActivity.this, AdminMainActivity.class);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(MainActivity.this, PlayerMainActivity.class);
                                startActivity(i);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void register() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Email and password cannot be empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Registration successful.",
                                    Toast.LENGTH_SHORT).show();
                            login();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
