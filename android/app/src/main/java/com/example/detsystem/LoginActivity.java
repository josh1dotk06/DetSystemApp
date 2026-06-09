package com.example.detsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginSubmitButton;
    private Button backFromLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginSubmitButton = findViewById(R.id.loginSubmitButton);
        backFromLoginButton = findViewById(R.id.backFromLoginButton);

        loginSubmitButton.setOnClickListener(view -> {
            Toast.makeText(this, "Login backend not connected yet", Toast.LENGTH_SHORT).show();
        });

        backFromLoginButton.setOnClickListener(view -> {
            finish();
        });
    }
}