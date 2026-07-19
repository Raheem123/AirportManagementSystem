USE airport_management_system;

CREATE TABLE passenger (
    passenger_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(254) UNIQUE,
    phone VARCHAR(25),
    passport_number VARCHAR(30) UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);