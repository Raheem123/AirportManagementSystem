USE airport_management_system;

CREATE TABLE aircraft (
    aircraft_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(15) NOT NULL UNIQUE,
    model VARCHAR(80) NOT NULL,
    capacity SMALLINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_aircraft_capacity CHECK (capacity > 0)
);