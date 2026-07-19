USE airport_management_system;

CREATE TABLE flight_schedule (
    schedule_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    airline_code VARCHAR(3) NOT NULL,
    flight_number VARCHAR(10) NOT NULL,
    departure_airport_id INT UNSIGNED NOT NULL,
    arrival_airport_id INT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_flight_schedule_route
        UNIQUE (airline_code, flight_number,
                departure_airport_id, arrival_airport_id),

    CONSTRAINT chk_schedule_different_airports
        CHECK (departure_airport_id <> arrival_airport_id),

    CONSTRAINT fk_schedule_departure_airport
        FOREIGN KEY (departure_airport_id)
        REFERENCES airport (airport_id),

    CONSTRAINT fk_schedule_arrival_airport
        FOREIGN KEY (arrival_airport_id)
        REFERENCES airport (airport_id)
);