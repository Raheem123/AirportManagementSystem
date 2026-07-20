package com.airportmanagement.view;

import java.time.LocalDateTime;

public class FlightAvailability {
    private final int instanceId;
    private final String flightCode;
    private final String route;
    private final LocalDateTime departureTime;
    private final String aircraftModel;
    private final int capacity;
    private final int bookedSeats;

    public FlightAvailability(int instanceId, String flightCode, String route,
                              LocalDateTime departureTime, String aircraftModel,
                              int capacity, int bookedSeats) {
        this.instanceId = instanceId;
        this.flightCode = flightCode;
        this.route = route;
        this.departureTime = departureTime;
        this.aircraftModel = aircraftModel;
        this.capacity = capacity;
        this.bookedSeats = bookedSeats;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getRoute() {
        return route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public int getAvailableSeats() {
        return capacity - bookedSeats;
    }
}
