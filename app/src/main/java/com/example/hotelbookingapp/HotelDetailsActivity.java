package com.example.hotelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class HotelDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        Hotel hotel = (Hotel) getIntent().getSerializableExtra("selected_hotel");

        String checkInDate = getIntent().getStringExtra("check_in_date");
        String checkOutDate = getIntent().getStringExtra("check_out_date");

        assert checkInDate != null;
        int checkInYear = Integer.parseInt(checkInDate.substring(6, 10));
        int checkInMonth = Integer.parseInt(checkInDate.substring(3, 5));
        int checkInDay = Integer.parseInt(checkInDate.substring(0, 2));

        assert checkOutDate != null;
        int checkOutYear = Integer.parseInt(checkOutDate.substring(6, 10));
        int checkOutMonth = Integer.parseInt(checkOutDate.substring(3, 5));
        int checkOutDay = Integer.parseInt(checkOutDate.substring(0, 2));

        int numOfDays;
        if (checkOutYear == checkInYear && checkOutMonth == checkInMonth) {
            numOfDays = checkOutDay - checkInDay;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(checkInYear, checkInMonth - 1, checkInDay);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            numOfDays = daysInMonth - checkInDay + checkOutDay;
        }

        double roomPricePerNight = getIntent().getDoubleExtra("room_price_per_night", 0.0);

        if (hotel != null) {
            TextView textViewHotelName = findViewById(R.id.textViewHotelName);
            TextView textViewLocation = findViewById(R.id.textViewLocation);
            TextView textViewDescription = findViewById(R.id.textViewDescription);
            TextView textViewPrice = findViewById(R.id.textViewPrice);

            ImageView imageViewHotel = findViewById(R.id.imageViewHotel);

            textViewHotelName.setText(hotel.getName());
            textViewLocation.setText(hotel.getLocation());
            textViewDescription.setText(hotel.getDescription());

            textViewPrice.setText(String.format(Locale.getDefault(), "%.2f", roomPricePerNight));

            double cheapestRoomPrice = Double.MAX_VALUE;
            for (Room room : hotel.getRooms()) {
                double roomPrice = room.getPricePerNight();
                if (roomPrice < cheapestRoomPrice) {
                    cheapestRoomPrice = roomPrice;
                }
            }

            if (cheapestRoomPrice != Double.MAX_VALUE) {
                double totalRoomPrice = cheapestRoomPrice * numOfDays;
                textViewPrice.setText(String.format(Locale.getDefault(), "Цена на стаята: %.2f лв.", totalRoomPrice));
            } else {
                textViewPrice.setText("Няма налични стаи за избраните дати и брой гости");
            }

            Picasso.get().load(hotel.getImageUrl()).into(imageViewHotel);

            Button reserveButton = findViewById(R.id.buttonReserve);

            reserveButton.setOnClickListener(v -> {
                Intent intent = new Intent(HotelDetailsActivity.this, SuccessReservationActivity.class);
                startActivity(intent);
            });
        } else {
            Toast.makeText(this, "Грешка: Неуспешно получаване на данни за хотела", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
