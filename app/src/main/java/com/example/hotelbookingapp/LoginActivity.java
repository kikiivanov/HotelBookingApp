package com.example.hotelbookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(v -> loginUser());

        textViewRegister.setOnClickListener(v -> redirectToRegistration());
    }

    private void redirectToRegistration() {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(this, "Паролата трябва да бъде поне 8 символа", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        boolean isLoginSuccessful = email.equals(savedEmail) && password.equals(savedPassword);

        if (isLoginSuccessful) {
            startActivity(new Intent(this, SearchHotelsActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Невалидно потребителско име или парола", Toast.LENGTH_SHORT).show();
            boolean isRegistered = sharedPreferences.contains(email);
            if (isRegistered) {
                Toast.makeText(this, "Потребителят вече съществува", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Няма регистриран потребител с това име", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, RegistrationActivity.class));
            }
        }
    }
}
