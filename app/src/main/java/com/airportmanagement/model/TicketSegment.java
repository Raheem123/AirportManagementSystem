package com.airportmanagement.model;

import java.math.BigDecimal;

public class TicketSegment {

    private int segmentId;
    private int ticketId;
    private int flightInstanceId;
    private int segmentNumber;
    private String seatNumber;
    private String cabinClass;
    private BigDecimal ticketPrice;
    private String segmentStatus;

    public TicketSegment() {
    }

    public TicketSegment(int segmentId, int ticketId, int flightInstanceId,
                         int segmentNumber, String seatNumber,
                         String cabinClass, BigDecimal ticketPrice,
                         String segmentStatus) {
        this.segmentId = segmentId;
        this.ticketId = ticketId;
        this.flightInstanceId = flightInstanceId;
        this.segmentNumber = segmentNumber;
        this.seatNumber = seatNumber;
        this.cabinClass = cabinClass;
        this.ticketPrice = ticketPrice;
        this.segmentStatus = segmentStatus;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getFlightInstanceId() {
        return flightInstanceId;
    }

    public void setFlightInstanceId(int flightInstanceId) {
        this.flightInstanceId = flightInstanceId;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(int segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getSegmentStatus() {
        return segmentStatus;
    }

    public void setSegmentStatus(String segmentStatus) {
        this.segmentStatus = segmentStatus;
    }
}
