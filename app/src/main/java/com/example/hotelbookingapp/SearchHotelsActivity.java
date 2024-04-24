package com.example.hotelbookingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchHotelsActivity extends AppCompatActivity {

    EditText editTextCheckInDate;
    EditText editTextCheckOutDate;
    EditText editTextGuests;
    Button buttonSearchHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_hotels);

        editTextCheckInDate = findViewById(R.id.editTextCheckInDate);
        editTextCheckOutDate = findViewById(R.id.editTextCheckOutDate);
        buttonSearchHotels = findViewById(R.id.buttonSearchHotels);
        editTextGuests = findViewById(R.id.editTextGuests);

        buttonSearchHotels.setOnClickListener(v -> searchHotels());

        editTextCheckInDate.setOnClickListener(v -> showDatePickerDialog(editTextCheckInDate));

        editTextCheckOutDate.setOnClickListener(v -> showDatePickerDialog(editTextCheckOutDate));
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = getDatePickerDialog(editText);
        datePickerDialog.show();
    }

    @NonNull
    private DatePickerDialog getDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    calendar.set(year1, month1, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    editText.setText(dateFormat.format(calendar.getTime()));
                },
                year, month, day);
    }

    private boolean isDateAfterToday(String date) {
        if (date.length() < 10) {
            return false;
        }

        int todayYear, todayMonth, todayDay;
        Calendar calendar = Calendar.getInstance();
        todayYear = calendar.get(Calendar.YEAR);
        todayMonth = calendar.get(Calendar.MONTH) + 1;
        todayDay = calendar.get(Calendar.DAY_OF_MONTH);

        int checkYear = Integer.parseInt(date.substring(6, 10));
        int checkMonth = Integer.parseInt(date.substring(3, 5));
        int checkDay = Integer.parseInt(date.substring(0, 2));

        if (checkYear < todayYear) {
            return true;
        } else if (checkYear == todayYear && checkMonth < todayMonth) {
            return true;
        } else if (checkYear == todayYear && checkMonth == todayMonth && checkDay < todayDay) {
            return true;
        }

        return false;
    }

    private boolean isCheckOutAfterCheckIn(String checkInDate, String checkOutDate) {
        if (checkInDate.length() < 10 || checkOutDate.length() < 10) {
            return false;
        }

        int checkInYear = Integer.parseInt(checkInDate.substring(6, 10));
        int checkInMonth = Integer.parseInt(checkInDate.substring(3, 5));
        int checkInDay = Integer.parseInt(checkInDate.substring(0, 2));

        int checkOutYear = Integer.parseInt(checkOutDate.substring(6, 10));
        int checkOutMonth = Integer.parseInt(checkOutDate.substring(3, 5));
        int checkOutDay = Integer.parseInt(checkOutDate.substring(0, 2));

        if (checkOutYear < checkInYear) {
            return false;
        } else if (checkOutYear == checkInYear && checkOutMonth < checkInMonth) {
            return false;
        } else if (checkOutYear == checkInYear && checkOutMonth == checkInMonth && checkOutDay <= checkInDay) {
            return false;
        }

        return true;
    }

    private void searchHotels() {
        String checkInDate = editTextCheckInDate.getText().toString();
        String checkOutDate = editTextCheckOutDate.getText().toString();
        String guests = editTextGuests.getText().toString();

        if (checkInDate.isEmpty() || checkOutDate.isEmpty() || guests.isEmpty()) {
            Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Дата на настаняване: " + checkInDate +
                "\nДата на напускане: " + checkOutDate +
                "\nБрой гости: " + guests;

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        if (Integer.parseInt(guests) > 4 || Integer.parseInt(guests) == 0) {
            Toast.makeText(this, "Няма налични хотели за въведения брой гости", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("check_in_date", checkInDate);
        intent.putExtra("check_out_date", checkOutDate);
        intent.putExtra("guests", guests);

        if (isDateAfterToday(checkInDate) || isDateAfterToday(checkOutDate) || !isCheckOutAfterCheckIn(checkInDate, checkOutDate)) {
            Toast.makeText(this, "Грешка: Моля, проверете датите за настаняване и напускане", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(intent);
        }
    }
}