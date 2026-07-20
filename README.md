# Airport Management System

A desktop-based airport management system built with Java, Java Swing, MySQL, JDBC, and Maven. It manages passengers, flight operations, direct ticket bookings, baggage movement across connecting flights, and staff assignments.

## Features

- Passenger CRUD: add, view, update, and delete passenger records.
- Flight availability: view a flight instance's capacity, booked seats, and remaining seats.
- Direct booking: create a confirmed ticket and its flight segment atomically.
- Baggage tracking: record a physical bag's status separately for every flight leg.
- Staff operations: register staff and assign them to a specific flight instance with a duty.

## Database design

The schema is normalized to Third Normal Form (3NF). The `flight_schedule` table represents a repeating route (for example, AI101), while `flight_instance` represents one actual departure on a particular date.

For connecting journeys, one `ticket` can have multiple `ticket_segment` records. A `baggage_segment` then connects each physical bag to each ticket segment, so the bag's location is clear during a layover.

## Transaction and rollback

Creating a booking is a single JDBC transaction:

1. The selected flight capacity is locked and checked.
2. A ticket is inserted.
3. Its ticket segment is inserted.
4. Both inserts are committed only when every step succeeds.

If a seat is already taken or any database operation fails, the transaction is rolled back. This prevents a ticket being stored without its flight segment and demonstrates ACID consistency.

## Technology stack

- Java 11+
- Java Swing
- MySQL 8+
- MySQL Connector/J
- Maven
- Git and GitHub

## Project structure

```text
AirportManagementSystem/
├── Database/                    # Ordered MySQL schema and seed scripts
├── app/
│   ├── src/main/java/           # Java source code
│   ├── src/main/resources/      # Local database configuration
│   └── pom.xml                  # Maven dependencies and build settings
├── docs/                        # Manual test checklist and screenshot guide
└── README.md
```

## Setup and run

1. In MySQL Workbench, run the SQL files in `Database` in numerical order: `01_create_database.sql` through `13_seed_sample_data.sql`.
2. Open the `app` folder as a Maven project in IntelliJ IDEA.
3. Copy `app/src/main/resources/db.properties.example` to `db.properties` in the same folder.
4. Set your MySQL username and password in `db.properties`. Do not commit this file.
5. Select Java 11 or a newer JDK in IntelliJ.
6. Choose **Build → Rebuild Project**.
7. Run `com.airportmanagement.Application`.

The main menu opens the Passenger, Booking, Baggage Tracking, and Staff Operations screens.

## Sample data

The seed script creates passenger Aisha Rahman, two connecting flights (DEL → DXB and DXB → LHR), a ticket with two segments, and one bag tracked across both legs. This makes the connecting-flight workflow immediately visible.

## Testing and screenshots

Follow [docs/TESTING.md](docs/TESTING.md) to test the application and capture evidence for the GitHub repository or a project PDF.

## Future improvements

- Add a multi-leg booking workflow to the Swing interface.
- Add user login and role-based access control.
- Add search and reporting screens.
- Add automated JUnit tests and a database migration tool such as Flyway.
