package com.example.hotelbookingapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hotel implements Serializable {
    private UUID hotelId;
    private String name;
    private List<Room> rooms;
    private String location;
    private String description;
    private String imageUrl;

    public Hotel(UUID hotelId, String name, List<Room> rooms, String location, String description, String imageUrl) {
        this.hotelId = hotelId;
        this.name = name;
        this.rooms = rooms;
        this.location = location;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
