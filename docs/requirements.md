# System Requirements



## Project Objective

Build a Java Swing and MySQL application to manage airport passengers, flights, staff, baggage, and bookings.



## Users

- Administrator

- Airport staff



## Core Features

- Manage passenger records

- Manage flight records and schedules

- Manage staff records

- Manage baggage records

- Create and manage flight bookings



## Business Rules

- Each booking belongs to one passenger and one flight.

- Each baggage item belongs to one passenger.

- A booking cannot be created if the flight has no available seats.



## Transaction Requirement

Creating a booking must save the booking and reduce the flight's available seats in one transaction. If either operation fails, all changes must be rolled back.



## Out of Scope

- Online payments

- Live flight data APIs

- GPS baggage tracking

