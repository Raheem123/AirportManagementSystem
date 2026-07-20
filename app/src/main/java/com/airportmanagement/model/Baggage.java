package com.airportmanagement.model;

import java.math.BigDecimal;

public class Baggage {

    private int baggageId;
    private int ticketId;
    private String tagNumber;
    private BigDecimal weightKg;
    private String baggageStatus;

    public Baggage() {
    }

    public Baggage(int baggageId, int ticketId, String tagNumber,
                   BigDecimal weightKg, String baggageStatus) {
        this.baggageId = baggageId;
        this.ticketId = ticketId;
        this.tagNumber = tagNumber;
        this.weightKg = weightKg;
        this.baggageStatus = baggageStatus;
    }

    public int getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(int baggageId) {
        this.baggageId = baggageId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public String getBaggageStatus() {
        return baggageStatus;
    }

    public void setBaggageStatus(String baggageStatus) {
        this.baggageStatus = baggageStatus;
    }
}
