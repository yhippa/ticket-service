package io.github.yhippa;

import io.github.yhippa.entities.SeatHold;
import io.github.yhippa.entities.Venue;
import io.github.yhippa.services.TicketService;
import io.github.yhippa.services.TicketServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by richardyhip on 3/29/17.
 */
public class TicketServiceTest {
    private TicketService testTicketService = new TicketServiceImpl();
    private final static int SEAT_CAPACITY = 30;
    private int seatsToHold;
    private final static String CUSTOMER_EMAIL_ADDRESS = "test.customer@example.org";

    @Before
    public void setup() {

    }

    @Test
    public void testNumSeatsAvailable() {
        assertEquals("failure - seats available does not equal seats created", SEAT_CAPACITY, testTicketService.numSeatsAvailable());
    }

    @Test
    public void testFindAndHoldSeats() {
        seatsToHold = 5;
        SeatHold seatsHeld = testTicketService.findAndHoldSeats(seatsToHold, CUSTOMER_EMAIL_ADDRESS);
        System.out.println(seatsHeld);
        seatsHeld = testTicketService.findAndHoldSeats(5, CUSTOMER_EMAIL_ADDRESS);
        System.out.println(seatsHeld);
        seatsHeld = testTicketService.findAndHoldSeats(8, CUSTOMER_EMAIL_ADDRESS);
        System.out.println(seatsHeld);
        assertEquals("failure - seats available do not account for initial capacity less two reserved seats", 8, seatsHeld.getSeatNumbersHeld().size());
    }

    @Test
    public void testReserveSeats() throws Exception {
        assertEquals("failure - couldn't reserve seats", "RESERVED", testTicketService.reserveSeats(1, CUSTOMER_EMAIL_ADDRESS));
    }
}
