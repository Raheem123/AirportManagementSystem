package com.airportmanagement.model;

import java.time.LocalDateTime;

public class BaggageSegment {

    private int baggageSegmentId;
    private int baggageId;
    private int ticketSegmentId;
    private String loadStatus;
    private LocalDateTime loadedAt;
    private LocalDateTime unloadedAt;

    public BaggageSegment() {
    }

    public BaggageSegment(int baggageSegmentId, int baggageId,
                          int ticketSegmentId, String loadStatus,
                          LocalDateTime loadedAt,
                          LocalDateTime unloadedAt) {
        this.baggageSegmentId = baggageSegmentId;
        this.baggageId = baggageId;
        this.ticketSegmentId = ticketSegmentId;
        this.loadStatus = loadStatus;
        this.loadedAt = loadedAt;
        this.unloadedAt = unloadedAt;
    }

    public int getBaggageSegmentId() {
        return baggageSegmentId;
    }

    public void setBaggageSegmentId(int baggageSegmentId) {
        this.baggageSegmentId = baggageSegmentId;
    }

    public int getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(int baggageId) {
        this.baggageId = baggageId;
    }

    public int getTicketSegmentId() {
        return ticketSegmentId;
    }

    public void setTicketSegmentId(int ticketSegmentId) {
        this.ticketSegmentId = ticketSegmentId;
    }

    public String getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(String loadStatus) {
        this.loadStatus = loadStatus;
    }

    public LocalDateTime getLoadedAt() {
        return loadedAt;
    }

    public void setLoadedAt(LocalDateTime loadedAt) {
        this.loadedAt = loadedAt;
    }

    public LocalDateTime getUnloadedAt() {
        return unloadedAt;
    }

    public void setUnloadedAt(LocalDateTime unloadedAt) {
        this.unloadedAt = unloadedAt;
    }
}
