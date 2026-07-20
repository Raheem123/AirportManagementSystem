package com.airportmanagement.model;


public class Airport {

    private int airportId;
    private String iataCode;
    private String name;
    private String city;
    private String country;

    public Airport() {
    }

    public Airport(int airportId, String iataCode, String name,
                   String city, String country) {
        this.airportId = airportId;
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public int getAirportId() {
        return airportId;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
