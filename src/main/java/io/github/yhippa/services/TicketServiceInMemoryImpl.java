package io.github.yhippa.services;

import io.github.yhippa.entities.Seat;
import io.github.yhippa.entities.SeatHold;
import io.github.yhippa.enums.HoldStatus;
import io.github.yhippa.enums.Validity;

import java.util.*;
import java.util.logging.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TicketServiceInMemoryImpl implements TicketService {
    private final static Logger LOGGER = Logger.getLogger(TicketServiceInMemoryImpl.class.getName());
    private static final int SEAT_CAPACITY = 100;
    private static final String TICKET_PREFIX = "RUNDMC";
    private static int ticketIdGenerator = 0;
    private static final int HOLD_TIME = 5;

    private Map<Integer, Seat> seatingChart = new LinkedHashMap<>();
    private Map<Integer, SeatHold> seatHolds = new LinkedHashMap<>();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(10);

    public TicketServiceInMemoryImpl() {
        for (int i = 1; i <= SEAT_CAPACITY; i++) {
            seatingChart.put(i, new Seat());
        }
        LOGGER.info("Ticket Service in-memory implementation created with " + SEAT_CAPACITY + " seats");
    }

    public int numSeatsAvailable() {
        return (int) seatingChart.entrySet().stream().filter(map -> HoldStatus.AVAILABLE.equals(map.getValue().getAvailability())).count();
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        List<Integer> seatsHeld = new ArrayList<>();
        // create a new seatHold to get id
        SeatHold seatHold = new SeatHold(seatsHeld, customerEmail);

        seatingChart.entrySet().stream().filter(seat -> HoldStatus.AVAILABLE.equals(seat.getValue().getAvailability())).sorted(Comparator.comparingInt(Map.Entry::getKey)).limit(numSeats).forEach(seat -> {
            seat.getValue().setAvailability(HoldStatus.HELD);
            seat.getValue().setSeatHoldId(seatHold.getSeatHoldId());
            seatsHeld.add(seat.getKey());
        });

        final Runnable holdTimeout = () -> {
            List<Integer> seats = seatHold.getSeatNumbersHeld();
            for (int seat : seats) {
                if (seatingChart.get(seat).getAvailability().equals(HoldStatus.HELD)) {
                    seatingChart.get(seat).setAvailability(HoldStatus.AVAILABLE);
                    seatingChart.get(seat).setSeatHoldId(0);
                    LOGGER.info(seatHold.getSeatHoldId() + " released");
                } else {
                    LOGGER.info("Seat was booked within time");
                }
            }
            seatHold.setValidity(Validity.INVALID);
        };
        scheduler.schedule(holdTimeout, HOLD_TIME, TimeUnit.SECONDS);
        seatHolds.put(seatHold.getSeatHoldId(), seatHold);
        return seatHold;
    }

    public void printSeatingChart() {
        seatingChart.entrySet().stream().forEach(map -> System.out.println(map.getKey() + ", " + map.getValue()));
    }

    public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold hold = seatHolds.get(seatHoldId);
        List<Integer> seatsHeld = hold.getSeatNumbersHeld();
        if (hold.getEmailAddress().equals(customerEmail) && (seatHoldId == hold.getSeatHoldId()) && (seatsHeld.size() > 0) && (hold.getValidity() == Validity.VALID)) {
            for (int seatNumber : seatsHeld) {
                Seat seat = seatingChart.get(seatNumber);
                if (seat.getAvailability() == HoldStatus.HELD) {
                    seat.setAvailability(HoldStatus.RESERVED);
                    seat.setSeatHoldId(hold.getSeatHoldId());
                }
            }
            return TICKET_PREFIX + ticketIdGenerator++;
        }
        return "INVALID";
    }
}
