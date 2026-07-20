package com.airportmanagement.model;

import java.time.LocalDateTime;

public class Ticket {

    private int ticketId;
    private String bookingReference;
    private int passengerId;
    private LocalDateTime bookedAt;
    private String ticketStatus;

    public Ticket() {
    }

    public Ticket(int ticketId, String bookingReference, int passengerId,
                  LocalDateTime bookedAt, String ticketStatus) {
        this.ticketId = ticketId;
        this.bookingReference = bookingReference;
        this.passengerId = passengerId;
        this.bookedAt = bookedAt;
        this.ticketStatus = ticketStatus;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
