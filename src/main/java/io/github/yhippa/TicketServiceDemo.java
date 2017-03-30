package io.github.yhippa;

import io.github.yhippa.entities.SeatHold;
import io.github.yhippa.services.TicketService;
import io.github.yhippa.services.TicketServiceInMemoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class TicketServiceDemo {
    private final static Logger LOGGER = Logger.getLogger(TicketServiceDemo.class.getName());
    public static void main( String[] args ) {
        TicketService ticketService = new TicketServiceInMemoryImpl();
        LOGGER.info("Seats available: " + ticketService.numSeatsAvailable());
        List<SeatHold> seatHolds = new ArrayList<>();

        // Lambda Runnable
        Runnable holdSeats = () -> {
            SeatHold seatHold = ticketService.findAndHoldSeats(ThreadLocalRandom.current().nextInt(1, 8), "user" + ThreadLocalRandom.current().nextInt(0, 99999) + "@example.org");
            LOGGER.info("Seat hold: " + seatHold);
            seatHolds.add(seatHold);

        };

        Runnable reserveSeats = () -> {
            for (SeatHold seatHold : seatHolds) {
                if (ThreadLocalRandom.current().nextInt(2) == 0) {
                    String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), seatHold.getEmailAddress());
                    LOGGER.info("Reservation code: " + reservationCode);
                }
            }
        };

        // start the thread
        LOGGER.info("Hold seats for 100 users:");
        for (int i = 0; i < 100; i++) {
            new Thread(holdSeats).start();
        }

        LOGGER.info("Wait 2 seconds:");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Reserving seats...");
        new Thread(reserveSeats).start();

        LOGGER.info("Wait 10 seconds...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("After first wave, 10 more random users book tickets");
        for (int i = 0; i < 10; i++) {
            new Thread(holdSeats).start();
        }

        LOGGER.info("Wait 2 seconds...:");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        LOGGER.info("Reserving held seats...:");
        new Thread(reserveSeats).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
