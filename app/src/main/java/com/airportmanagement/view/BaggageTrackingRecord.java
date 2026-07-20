package com.airportmanagement.view;

import java.time.LocalDateTime;

public class BaggageTrackingRecord {
    private final int baggageSegmentId;
    private final String tagNumber;
    private final String bookingReference;
    private final int segmentNumber;
    private final String flightCode;
    private final String route;
    private final LocalDateTime departureTime;
    private final String loadStatus;
    private final LocalDateTime loadedAt;
    private final LocalDateTime unloadedAt;

    public BaggageTrackingRecord(int baggageSegmentId, String tagNumber, String bookingReference,
                                 int segmentNumber, String flightCode, String route,
                                 LocalDateTime departureTime, String loadStatus,
                                 LocalDateTime loadedAt, LocalDateTime unloadedAt) {
        this.baggageSegmentId = baggageSegmentId;
        this.tagNumber = tagNumber;
        this.bookingReference = bookingReference;
        this.segmentNumber = segmentNumber;
        this.flightCode = flightCode;
        this.route = route;
        this.departureTime = departureTime;
        this.loadStatus = loadStatus;
        this.loadedAt = loadedAt;
        this.unloadedAt = unloadedAt;
    }

    public int getBaggageSegmentId() { return baggageSegmentId; }
    public String getTagNumber() { return tagNumber; }
    public String getBookingReference() { return bookingReference; }
    public int getSegmentNumber() { return segmentNumber; }
    public String getFlightCode() { return flightCode; }
    public String getRoute() { return route; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public String getLoadStatus() { return loadStatus; }
    public LocalDateTime getLoadedAt() { return loadedAt; }
    public LocalDateTime getUnloadedAt() { return unloadedAt; }
}
