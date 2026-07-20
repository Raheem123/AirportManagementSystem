package com.airportmanagement.model;

public class FlightSchedule {

    private int scheduleId;
    private String airlineCode;
    private String flightNumber;
    private int departureAirportId;
    private int arrivalAirportId;

    public FlightSchedule() {
    }

    public FlightSchedule(int scheduleId, String airlineCode,
                          String flightNumber, int departureAirportId,
                          int arrivalAirportId) {
        this.scheduleId = scheduleId;
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(int departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public int getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(int arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }
}
