package io.github.yhippa.services;

import io.github.yhippa.entities.Seat;
import io.github.yhippa.entities.SeatHold;
import io.github.yhippa.enums.HoldStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by richardyhip on 3/29/17.
 */
public class TicketServiceImpl implements TicketService{
    private static final int seatCapacity = 30;
    // master seating chart the whole app will use
    Map<Integer, Seat> seatingChart = new HashMap<Integer, Seat>();

    public TicketServiceImpl() {
        for (int i = 1; i <= 30; i++) {
            seatingChart.put(i, new Seat());
        }
    }
    public int numSeatsAvailable() {
        return (int) seatingChart.entrySet().stream().filter(map -> HoldStatus.AVAILABLE.equals(map.getValue().getAvailability())).count();
    }

    public synchronized SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        //1. get available seats
        //2. apply holds to the numseats
        //3 make seathold object and return
        List<Integer> seatsHeld = new ArrayList<Integer>();
        List<Map.Entry<Integer, Seat>> collect = seatingChart.entrySet().stream().filter(map -> HoldStatus.AVAILABLE.equals(map.getValue().getAvailability())).collect(Collectors.toList());
        if (numSeats > collect.size()) {
            return new SeatHold(seatsHeld, customerEmail);
        } else {
            for (int i = 0; i < numSeats; i ++) {
                seatingChart.get(collect.get(i).getKey()).setAvailability(HoldStatus.HELD);
                seatsHeld.add(collect.get(i).getKey());
            }
            return new SeatHold(seatsHeld, customerEmail);
        }
    }

    void printSeatingChart() {
        seatingChart.entrySet().stream().forEach(map -> System.out.println(map.getKey() + ", " + map.getValue()));
    }

    public String reserveSeats(int seatHoldId, String customerEmail) {
        return null;
    }
}
