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
        TicketServiceInMemoryImpl ticketService = new TicketServiceInMemoryImpl();
        LOGGER.info("Initial seats available: " + ticketService.numSeatsAvailable());
        LOGGER.info("Initial seating chart:");
        ticketService.printSeatingChart();
        List<SeatHold> seatHolds = new ArrayList<>();

        Runnable holdSeats = () -> {
            synchronized (seatHolds) {
                SeatHold seatHold = ticketService.findAndHoldSeats(ThreadLocalRandom.current().nextInt(1, 8), "user" + ThreadLocalRandom.current().nextInt(0, 99999) + "@example.org");
                seatHolds.add(seatHold);
            }
        };

        Runnable reserveSeats = () -> {
            synchronized (seatHolds) {
                for (SeatHold seatHold : seatHolds) {
                    if (ThreadLocalRandom.current().nextInt(2) == 0) {
                        LOGGER.info("Reserving seat hold id " + seatHold.getSeatHoldId() + " for customer " + seatHold.getEmailAddress());
                        String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), seatHold.getEmailAddress());
                        LOGGER.info(seatHold.getSeatHoldId() + " reservation result " + reservationCode);
                    }
                }
            }
        };

        // start the thread
        LOGGER.info("Simulating ticket demand for 100 customers...");
        for (int i = 0; i < 100; i++) {
            new Thread(holdSeats).start();
        }

        LOGGER.info("Random 50% of customers trying to reserve seats...");
        new Thread(reserveSeats).start();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("After first wave, 10 more random users book tickets");
        for (int i = 0; i < 10; i++) {
            new Thread(holdSeats).start();
        }

        LOGGER.info("Random 50% of customers trying to reserve seats...");
        new Thread(reserveSeats).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Final seats available: " + ticketService.numSeatsAvailable());
        LOGGER.info("Final seating chart:");
        ticketService.printSeatingChart();
        ticketService.shutdown();
    }
}
