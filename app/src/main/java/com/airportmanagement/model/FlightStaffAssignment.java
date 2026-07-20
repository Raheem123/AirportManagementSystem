package com.airportmanagement.model;

import java.time.LocalDateTime;

public class FlightStaffAssignment {

    private int assignmentId;
    private int staffId;
    private int flightInstanceId;
    private String duty;
    private LocalDateTime assignedAt;

    public FlightStaffAssignment() {
    }

    public FlightStaffAssignment(int assignmentId, int staffId,
                                 int flightInstanceId, String duty,
                                 LocalDateTime assignedAt) {
        this.assignmentId = assignmentId;
        this.staffId = staffId;
        this.flightInstanceId = flightInstanceId;
        this.duty = duty;
        this.assignedAt = assignedAt;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getFlightInstanceId() {
        return flightInstanceId;
    }

    public void setFlightInstanceId(int flightInstanceId) {
        this.flightInstanceId = flightInstanceId;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}
