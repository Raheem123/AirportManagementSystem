USE airport_management_system;

CREATE TABLE ticket (
    ticket_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    booking_reference CHAR(8) NOT NULL UNIQUE,
    passenger_id INT UNSIGNED NOT NULL,
    booked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ticket_status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',

    CONSTRAINT chk_ticket_status
        CHECK (ticket_status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED')),

    CONSTRAINT fk_ticket_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passenger (passenger_id)
);