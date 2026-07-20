package com.airportmanagement;

import com.airportmanagement.dao.PassengerDao;
import com.airportmanagement.model.Passenger;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        PassengerDao passengerDao = new PassengerDao();

        try {
            for (Passenger passenger : passengerDao.findAll()) {
                System.out.println(
                        passenger.getPassengerId() + " | "
                                + passenger.getFirstName() + " " + passenger.getLastName()
                );
            }
        } catch (SQLException exception) {
            System.err.println("Unable to load passengers from the database.");
            exception.printStackTrace();
        }
    }
}
