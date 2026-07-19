USE airport_management_system;

-- Airports
INSERT INTO airport (iata_code, name, city, country) VALUES
    ('DEL', 'Indira Gandhi International Airport', 'New Delhi', 'India'),
    ('DXB', 'Dubai International Airport', 'Dubai', 'United Arab Emirates'),
    ('LHR', 'Heathrow Airport', 'London', 'United Kingdom');

-- Aircraft
INSERT INTO aircraft (registration_number, model, capacity) VALUES
    ('VT-AMS1', 'Airbus A320', 180),
    ('A6-AMS2', 'Boeing 777-300ER', 354);

-- Staff
INSERT INTO staff (first_name, last_name, email, phone, job_title) VALUES
    ('Arjun', 'Mehta', 'arjun.mehta@airportdemo.com', '+91-9000000001', 'Gate Agent'),
    ('Sara', 'Khan', 'sara.khan@airportdemo.com', '+971-500000002', 'Baggage Handler');

-- Passenger
INSERT INTO passenger (
    first_name, last_name, date_of_birth, email, phone, passport_number
) VALUES (
    'Aisha', 'Rahman', '1998-05-14',
    'aisha.rahman@example.com', '+91-9000000003', 'P1234567'
);

-- Repeating flight schedules
INSERT INTO flight_schedule (
    airline_code, flight_number, departure_airport_id, arrival_airport_id
) VALUES
(
    'AI', '101',
    (SELECT airport_id FROM airport WHERE iata_code = 'DEL'),
    (SELECT airport_id FROM airport WHERE iata_code = 'DXB')
),
(
    'AI', '201',
    (SELECT airport_id FROM airport WHERE iata_code = 'DXB'),
    (SELECT airport_id FROM airport WHERE iata_code = 'LHR')
);

-- Actual flight operations on one day
INSERT INTO flight_instance (
    schedule_id, aircraft_id,
    scheduled_departure_time, scheduled_arrival_time, flight_status
) VALUES
(
    (SELECT schedule_id
     FROM flight_schedule
     WHERE airline_code = 'AI' AND flight_number = '101'),
    (SELECT aircraft_id
     FROM aircraft
     WHERE registration_number = 'VT-AMS1'),
    '2026-08-10 08:30:00',
    '2026-08-10 10:45:00',
    'SCHEDULED'
),
(
    (SELECT schedule_id
     FROM flight_schedule
     WHERE airline_code = 'AI' AND flight_number = '201'),
    (SELECT aircraft_id
     FROM aircraft
     WHERE registration_number = 'A6-AMS2'),
    '2026-08-10 13:00:00',
    '2026-08-10 17:30:00',
    'SCHEDULED'
);

-- One passenger ticket for a connecting journey
INSERT INTO ticket (
    booking_reference, passenger_id, ticket_status
) VALUES (
    'AMS26001',
    (SELECT passenger_id
     FROM passenger
     WHERE passport_number = 'P1234567'),
    'CONFIRMED'
);

-- Two legs: Delhi → Dubai → London
INSERT INTO ticket_segment (
    ticket_id, flight_instance_id, segment_number,
    seat_number, cabin_class, ticket_price, segment_status
) VALUES
(
    (SELECT ticket_id
     FROM ticket
     WHERE booking_reference = 'AMS26001'),
    (SELECT fi.instance_id
     FROM flight_instance fi
     JOIN flight_schedule fs ON fi.schedule_id = fs.schedule_id
     WHERE fs.airline_code = 'AI'
       AND fs.flight_number = '101'
       AND fi.scheduled_departure_time = '2026-08-10 08:30:00'),
    1, '14A', 'ECONOMY', 220.00, 'CONFIRMED'
),
(
    (SELECT ticket_id
     FROM ticket
     WHERE booking_reference = 'AMS26001'),
    (SELECT fi.instance_id
     FROM flight_instance fi
     JOIN flight_schedule fs ON fi.schedule_id = fs.schedule_id
     WHERE fs.airline_code = 'AI'
       AND fs.flight_number = '201'
       AND fi.scheduled_departure_time = '2026-08-10 13:00:00'),
    2, '22C', 'ECONOMY', 430.00, 'CONFIRMED'
);

-- One physical bag owned by the ticket
INSERT INTO baggage (
    ticket_id, tag_number, weight_kg, baggage_status
) VALUES (
    (SELECT ticket_id
     FROM ticket
     WHERE booking_reference = 'AMS26001'),
    'BG-AMS-1001', 18.50, 'IN_TRANSIT'
);

-- The same bag is tracked across both flight legs
INSERT INTO baggage_segment (
    baggage_id, ticket_segment_id, load_status, loaded_at, unloaded_at
) VALUES
(
    (SELECT baggage_id
     FROM baggage
     WHERE tag_number = 'BG-AMS-1001'),
    (SELECT segment_id
     FROM ticket_segment
     WHERE ticket_id = (
         SELECT ticket_id
         FROM ticket
         WHERE booking_reference = 'AMS26001'
     ) AND segment_number = 1),
    'UNLOADED', '2026-08-10 07:45:00', '2026-08-10 10:55:00'
),
(
    (SELECT baggage_id
     FROM baggage
     WHERE tag_number = 'BG-AMS-1001'),
    (SELECT segment_id
     FROM ticket_segment
     WHERE ticket_id = (
         SELECT ticket_id
         FROM ticket
         WHERE booking_reference = 'AMS26001'
     ) AND segment_number = 2),
    'LOADED', '2026-08-10 12:20:00', NULL
);

-- Staff assigned to individual flight operations
INSERT INTO flight_staff_assignment (
    staff_id, flight_instance_id, duty
) VALUES
(
    (SELECT staff_id
     FROM staff
     WHERE email = 'arjun.mehta@airportdemo.com'),
    (SELECT fi.instance_id
     FROM flight_instance fi
     JOIN flight_schedule fs ON fi.schedule_id = fs.schedule_id
     WHERE fs.airline_code = 'AI'
       AND fs.flight_number = '101'
       AND fi.scheduled_departure_time = '2026-08-10 08:30:00'),
    'Gate Agent'
),
(
    (SELECT staff_id
     FROM staff
     WHERE email = 'sara.khan@airportdemo.com'),
    (SELECT fi.instance_id
     FROM flight_instance fi
     JOIN flight_schedule fs ON fi.schedule_id = fs.schedule_id
     WHERE fs.airline_code = 'AI'
       AND fs.flight_number = '201'
       AND fi.scheduled_departure_time = '2026-08-10 13:00:00'),
    'Baggage Handler'
);