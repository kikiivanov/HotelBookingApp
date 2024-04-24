package com.example.hotelbookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class HotelAdapter extends ArrayAdapter<Hotel> {

    private final OnHotelClickListener listener;

    public HotelAdapter(Context context, List<Hotel> hotels, OnHotelClickListener listener) {
        super(context, 0, hotels);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.hotel_list_item, parent, false);
        }

        Hotel hotel = getItem(position);

        if (hotel != null) {
            TextView hotelNameTextView = listItemView.findViewById(R.id.hotelNameTextView);
            hotelNameTextView.setText(hotel.getName());

            ImageView imageViewHotel = listItemView.findViewById(R.id.imageViewHotel);
            if (hotel.getImageUrl() != null && !hotel.getImageUrl().isEmpty()) {
                Picasso.get().load(hotel.getImageUrl()).into(imageViewHotel);
            } else {
                imageViewHotel.setVisibility(View.GONE);
            }
        }

        listItemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHotelClick(hotel);
            }
        });

        return listItemView;
    }

    public interface OnHotelClickListener {
        void onHotelClick(Hotel hotel);
    }
}
