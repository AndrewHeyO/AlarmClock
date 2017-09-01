package com.andrew.alarmclock.data.entities;

public class CustomLocation {
    private double latitude;
    private double longitude;

    private Throwable error;

    public CustomLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CustomLocation(Throwable error) {
        this.error = error;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
