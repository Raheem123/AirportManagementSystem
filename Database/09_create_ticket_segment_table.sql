USE airport_management_system;

CREATE TABLE ticket_segment (
    segment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT UNSIGNED NOT NULL,
    flight_instance_id INT UNSIGNED NOT NULL,
    segment_number TINYINT UNSIGNED NOT NULL,
    seat_number VARCHAR(5),
    cabin_class VARCHAR(20) NOT NULL,
    ticket_price DECIMAL(10, 2) NOT NULL,
    segment_status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',

    CONSTRAINT uq_ticket_segment_order
        UNIQUE (ticket_id, segment_number),

    CONSTRAINT uq_instance_seat
        UNIQUE (flight_instance_id, seat_number),

    CONSTRAINT chk_segment_number
        CHECK (segment_number > 0),

    CONSTRAINT chk_cabin_class
        CHECK (cabin_class IN ('ECONOMY', 'BUSINESS', 'FIRST')),

    CONSTRAINT chk_ticket_price
        CHECK (ticket_price >= 0),

    CONSTRAINT chk_segment_status
        CHECK (segment_status IN
            ('CONFIRMED', 'CHECKED_IN', 'BOARDED', 'CANCELLED', 'COMPLETED')),

    CONSTRAINT fk_segment_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket (ticket_id),

    CONSTRAINT fk_segment_instance
        FOREIGN KEY (flight_instance_id)
        REFERENCES flight_instance (instance_id)
);