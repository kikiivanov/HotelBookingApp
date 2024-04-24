package com.example.hotelbookingapp;

import java.io.Serializable;

public class Room implements Serializable {
    private int roomId;
    private double pricePerNight;
    private int capacity;

    public Room(int roomId, double pricePerNight, int capacity) {
        this.roomId = roomId;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}