USE airport_management_system;

CREATE TABLE baggage (
    baggage_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT UNSIGNED NOT NULL,
    tag_number VARCHAR(20) NOT NULL UNIQUE,
    weight_kg DECIMAL(5, 2) NOT NULL,
    baggage_status VARCHAR(20) NOT NULL DEFAULT 'CHECKED_IN',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_baggage_weight
        CHECK (weight_kg > 0),

    CONSTRAINT chk_baggage_status
        CHECK (baggage_status IN
            ('CHECKED_IN', 'IN_TRANSIT', 'DELIVERED', 'MISSING')),

    CONSTRAINT fk_baggage_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket (ticket_id)
);