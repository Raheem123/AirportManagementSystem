USE airport_management_system;

CREATE TABLE flight_instance (
    instance_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    schedule_id INT UNSIGNED NOT NULL,
    aircraft_id INT UNSIGNED NOT NULL,
    scheduled_departure_time DATETIME NOT NULL,
    scheduled_arrival_time DATETIME NOT NULL,
    flight_status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_flight_instance_time
        UNIQUE (schedule_id, scheduled_departure_time),

    CONSTRAINT chk_instance_time_order
        CHECK (scheduled_arrival_time > scheduled_departure_time),

    CONSTRAINT chk_instance_status
        CHECK (flight_status IN
            ('SCHEDULED', 'BOARDING', 'DEPARTED', 'ARRIVED', 'CANCELLED')),

    CONSTRAINT fk_instance_schedule
        FOREIGN KEY (schedule_id)
        REFERENCES flight_schedule (schedule_id),

    CONSTRAINT fk_instance_aircraft
        FOREIGN KEY (aircraft_id)
        REFERENCES aircraft (aircraft_id)
);