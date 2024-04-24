package com.example.hotelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchResultsActivity extends AppCompatActivity {

    private List<Hotel> hotels;
    private Button backButton;
    private String checkInDate;
    private String checkOutDate;
    private String guests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        ListView listViewHotels = findViewById(R.id.listViewHotels);
        backButton = findViewById(R.id.buttonBack);

        hotels = generateHotelData();

        Intent intent = getIntent();
        if (intent != null) {
            checkInDate = intent.getStringExtra("check_in_date");
            checkOutDate = intent.getStringExtra("check_out_date");
            guests = intent.getStringExtra("guests");

            if (checkInDate == null || checkOutDate == null || guests == null || checkInDate.isEmpty() || checkOutDate.isEmpty() || guests.isEmpty()) {
                Toast.makeText(this, "Грешка: Липсват входни данни за търсене", Toast.LENGTH_SHORT).show();
            } else {
                hotels = getFilteredHotelList(guests);

                assert hotels != null;
                if (hotels.isEmpty()) {
                    Toast.makeText(this, "Няма налични хотели, отговарящи на вашите критерии", Toast.LENGTH_SHORT).show();
                } else {
                    setupAdapterAndBackButton(listViewHotels);
                }
            }
        } else {
            Toast.makeText(this, "Грешка: Липсва Intent", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupAdapterAndBackButton(ListView listViewHotels) {
        HotelAdapter hotelAdapter = new HotelAdapter(SearchResultsActivity.this, hotels, hotel -> {
            Intent intent = new Intent(SearchResultsActivity.this, HotelDetailsActivity.class);
            intent.putExtra("selected_hotel", hotel);
            intent.putExtra("check_in_date", checkInDate);
            intent.putExtra("check_out_date", checkOutDate);
            intent.putExtra("guests", guests);
            startActivity(intent);
        });

        listViewHotels.setAdapter(hotelAdapter);

        backButton.setOnClickListener(v -> goBack());
    }

    public void goBack() {
        Intent intent = new Intent(SearchResultsActivity.this, SearchHotelsActivity.class);
        startActivity(intent);
        finish();
    }

    private List<Hotel> getFilteredHotelList(String guests) {
        List<Hotel> filteredHotels = new ArrayList<>();

        int numOfGuests = Integer.parseInt(guests);

        if (numOfGuests <= 0) {
            Toast.makeText(this, "Броят на гостите трябва да бъде по-голям от нула", Toast.LENGTH_SHORT).show();
            return null;
        }

        for (Hotel hotel : hotels) {
            boolean hasAvailableRooms = false;
            for (Room room : hotel.getRooms()) {
                if (room.getCapacity() >= numOfGuests) {
                    hasAvailableRooms = true;
                    break;
                }
            }
            if (hasAvailableRooms) {
                filteredHotels.add(hotel);
            }
        }

        return filteredHotels;
    }

    private List<Hotel> generateHotelData() {
        List<Hotel> hotels = new ArrayList<>();

        // Hilton
        List<Room> hiltonRooms = new ArrayList<>();
        hiltonRooms.add(new Room(1, 50.0, 1));
        hiltonRooms.add(new Room(2, 80.0, 2));
        UUID hiltonId = UUID.randomUUID();
        hotels.add(new Hotel(hiltonId, "Hilton", hiltonRooms, "ж.к. Лозенец, Бл. „България“ 1, 1421 София", "Разкошен хотел с изключително централно местоположение в близост до НДК. Hilton предлага луксозно настаняване, модерни удобства и превъзходно обслужване, като същевременно е идеално разположение за разглеждане на забележителностите на града.", "https://i.imgur.com/9pdtjwS.jpg"));

        // Intercontinental
        List<Room> intercontinentalRooms = new ArrayList<>();
        intercontinentalRooms.add(new Room(1, 180.0, 1));
        intercontinentalRooms.add(new Room(2, 220.0, 2));
        intercontinentalRooms.add(new Room(3, 270.0, 3));
        UUID intercontinentalId = UUID.randomUUID();
        hotels.add(new Hotel(intercontinentalId, "Intercontinental", intercontinentalRooms, "София център, пл. „Народно събрание“ 4, 1000 София", "Елегантен и реномиран хотел, който се намира в историческия център на София, в близост до културни и исторически забележителности. Със своите изискани стаи, изобилство от удобства и изключително обслужване, Intercontinental предлага незабравимо преживяване.", "https://i.imgur.com/6TnNJAp.jpg"));

        // Grand Hotel Millennium
        List<Room> grandHotelRooms = new ArrayList<>();
        grandHotelRooms.add(new Room(1, 100.0, 1));
        grandHotelRooms.add(new Room(2, 210.0, 2));
        grandHotelRooms.add(new Room(3, 260.0, 4));
        UUID grandHotelId = UUID.randomUUID();
        hotels.add(new Hotel(grandHotelId, "Grand Hotel Millennium", grandHotelRooms, "Милениум център, Иван Вазов, бул. „Витоша“ 89B, 1463 София", "Модерен и стилен хотел, разположен в близост до НДК. Със своите удобни стаи, ресторанти с изискана кухня и разнообразни удобства, Grand Hotel Millennium е привлекателен избор за всеки, който търси комфорт и уют по време на престой в града.", "https://i.imgur.com/d2SJPAT.jpg"));

        // Hyatt Regency
        List<Room> hyattRooms = new ArrayList<>();
        hyattRooms.add(new Room(1, 160.0, 1));
        hyattRooms.add(new Room(2, 210.0, 2));
        hyattRooms.add(new Room(3, 250.0, 3));
        hyattRooms.add(new Room(3, 270.0, 4));
        UUID hyattId = UUID.randomUUID();
        hotels.add(new Hotel(hyattId, "Hyatt Regency", hyattRooms, "София център, бул. „Васил Левски“ Square, 1504 София", "Луксозен хотел, който се отличава с изключително удобно местоположение в центъра на София. Със своите изискани интериори, висок клас обслужване и богат избор от удобства, Hyatt Reggency предлага неповторимо преживяване за своите гости.", "https://i.imgur.com/i3ednC3.jpg"));

        return hotels;
    }
}
