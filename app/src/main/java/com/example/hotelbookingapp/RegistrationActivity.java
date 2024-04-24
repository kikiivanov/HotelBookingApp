package com.example.hotelbookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextMobileNumber;
    EditText editTextEmail;
    EditText editTextNewPassword;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> registerUser());
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String mobileNumber = editTextMobileNumber.getText().toString();
        String email = editTextEmail.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Моля, въведете валиден имейл адрес", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 8) {
            Toast.makeText(this, "Паролата трябва да бъде поне 8 символа", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (email.equals(savedEmail)) {
            Toast.makeText(this, "Имейл адресът вече съществува", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("mobileNumber", mobileNumber);
        editor.putString("email", email);
        editor.putString("password", newPassword);
        editor.apply();

        Toast.makeText(this, "Успешно регистриране!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));
    }
}
