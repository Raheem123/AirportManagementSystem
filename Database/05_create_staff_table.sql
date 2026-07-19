USE airport_management_system;

CREATE TABLE staff (
    staff_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    phone VARCHAR(25),
    job_title VARCHAR(80) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);