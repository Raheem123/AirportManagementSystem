USE airport_management_system;

CREATE TABLE baggage_segment (
    baggage_segment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    baggage_id INT UNSIGNED NOT NULL,
    ticket_segment_id INT UNSIGNED NOT NULL,
    load_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    loaded_at DATETIME NULL,
    unloaded_at DATETIME NULL,

    CONSTRAINT uq_baggage_segment
        UNIQUE (baggage_id, ticket_segment_id),

    CONSTRAINT chk_baggage_load_status
        CHECK (load_status IN ('PENDING', 'LOADED', 'UNLOADED', 'MISSING')),

    CONSTRAINT chk_baggage_time_order
        CHECK (
            unloaded_at IS NULL
            OR loaded_at IS NULL
            OR unloaded_at >= loaded_at
        ),

    CONSTRAINT fk_baggage_segment_baggage
        FOREIGN KEY (baggage_id)
        REFERENCES baggage (baggage_id),

    CONSTRAINT fk_baggage_segment_ticket_segment
        FOREIGN KEY (ticket_segment_id)
        REFERENCES ticket_segment (segment_id)
);