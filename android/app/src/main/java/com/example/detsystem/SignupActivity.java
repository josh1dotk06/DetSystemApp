package com.example.detsystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private Button signupSubmitButton;
    private Button backFromSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupSubmitButton = findViewById(R.id.signupSubmitButton);
        backFromSignupButton = findViewById(R.id.backFromSignupButton);

        signupSubmitButton.setOnClickListener(view -> {
            Toast.makeText(this, "Signup backend not connected yet", Toast.LENGTH_SHORT).show();
        });

        backFromSignupButton.setOnClickListener(view -> {
            finish();
        });
    }
}