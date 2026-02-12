package com.example.dogwalking.dto;

public class GeoResult {
    private String address;
    private Double latitude;
    private Double longitude;
    private boolean success;
    private String errorMessage;

    // конструкторы
    public GeoResult() {
    }

    // успех
    public GeoResult(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.success = true;
    }

    // ошибка
    public GeoResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}