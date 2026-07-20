# Manual Test Checklist

Run `Application.java` after completing **Build → Rebuild Project**. Capture a screenshot after each successful test. Do not show `db.properties` in a screenshot because it contains your local password.

## 1. Main menu

- Expected: the window shows four buttons: passengers, bookings, baggage tracking, and staff assignments.
- Screenshot: main menu.

## 2. Passenger CRUD

- Open **Manage Passengers**.
- Add a test passenger with a unique email and passport number.
- Select that passenger, change the phone number, and update it.
- Delete the test passenger after confirming the update worked.
- Expected: the table refreshes after each operation.
- Screenshot: passenger table containing the sample passenger and your added test record.

## 3. Flight availability and booking

- Open **View Flights and Create Booking**.
- Select a flight row.
- Enter passenger ID `1`, a new 8-character booking reference such as `AMS26003`, seat `15B`, cabin `ECONOMY`, and price `250.00`.
- Create the booking.
- Expected: a success dialog appears and the booked-seat count increases after refresh.
- Screenshot: availability table before or after the booking, plus success dialog if possible.

## 4. Rollback demonstration

- Select the same flight used above.
- Use a new booking reference, but enter the already booked seat `15B`.
- Expected: booking fails because the seat is unique for that flight. No partial ticket should be saved.
- Evidence: query `SELECT * FROM ticket WHERE booking_reference = 'YOUR_NEW_REFERENCE';` in MySQL Workbench. It should return no row.
- Screenshot: error dialog and/or the empty SQL query result.

## 5. Baggage tracking across a layover

- Open **Track Baggage by Flight Leg**.
- Find bag tag `BG-AMS-1001`.
- Expected: it occurs once for DEL → DXB and once for DXB → LHR, proving a bag is tracked per flight leg.
- Select a row, choose a status, and record the baggage scan.
- Screenshot: both rows for the same bag.

## 6. Staff assignment

- Open **Manage Staff and Flight Assignments**.
- Add a staff member using a unique email.
- Select that staff member and a flight instance, enter a duty such as `Gate Agent`, then assign them.
- Expected: success dialog. Repeating the same assignment should fail because the `(staff_id, flight_instance_id)` pair is unique.
- Screenshot: populated staff table and selected flight table.

## Suggested final evidence

Use 4–6 screenshots in your project PDF: main menu, passenger CRUD, booking, baggage by flight leg, staff assignment, and GitHub commit history. Include your GitHub repository link on the first or last page.
