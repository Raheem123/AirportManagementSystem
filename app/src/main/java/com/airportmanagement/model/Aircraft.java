package com.airportmanagement.model;

public class Aircraft {

    private int aircraftId;
    private String registrationNumber;
    private String model;
    private int capacity;

    public Aircraft() {
    }

    public Aircraft(int aircraftId, String registrationNumber,
                    String model, int capacity) {
        this.aircraftId = aircraftId;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.capacity = capacity;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
