USE airport_management_system;

CREATE TABLE flight_staff_assignment (
    assignment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    staff_id INT UNSIGNED NOT NULL,
    flight_instance_id INT UNSIGNED NOT NULL,
    duty VARCHAR(80) NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_staff_instance
        UNIQUE (staff_id, flight_instance_id),

    CONSTRAINT fk_assignment_staff
        FOREIGN KEY (staff_id)
        REFERENCES staff (staff_id),

    CONSTRAINT fk_assignment_instance
        FOREIGN KEY (flight_instance_id)
        REFERENCES flight_instance (instance_id)
);