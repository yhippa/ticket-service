package io.github.yhippa;

import io.github.yhippa.entities.SeatHold;
import io.github.yhippa.services.TicketService;
import io.github.yhippa.services.TicketServiceInMemoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TicketServiceTest {
    private TicketService testTicketService = new TicketServiceInMemoryImpl();
    private final static int SEAT_CAPACITY = 100;
    private int seatsToHold;
    private final static String CUSTOMER_EMAIL_ADDRESS = "test.customer@example.org";

    @Test
    public void testNumSeatsAvailable() {
        assertEquals("failure - seats available does not equal seats created", SEAT_CAPACITY, testTicketService.numSeatsAvailable());
    }

    @Test
    public void testFindAndHoldSeats() {
        seatsToHold = 5;
        SeatHold seatsHeld = testTicketService.findAndHoldSeats(seatsToHold, CUSTOMER_EMAIL_ADDRESS);
        assertEquals("failure - seats available do not account for initial capacity less two reserved seats", seatsToHold, seatsHeld.getSeatNumbersHeld().size());
    }

    @Test
    public void testReserveSeats() throws Exception {
        SeatHold seatsHeld = testTicketService.findAndHoldSeats(8, CUSTOMER_EMAIL_ADDRESS);
        assertEquals("failure - couldn't reserve seats", "ABCD0", testTicketService.reserveSeats(seatsHeld.getSeatHoldId(), CUSTOMER_EMAIL_ADDRESS));
    }

    @Test
    public void testUserMustUseCorrectHoldToReserveTicket() throws Exception {
        SeatHold customerASeatHold = testTicketService.findAndHoldSeats(8, "customer.a@example.org");
        SeatHold customerBSeatHold = testTicketService.findAndHoldSeats(8, "customer.b@example.org");
        assertEquals("failure - customer B able to reserve seats", "INVALID", testTicketService.reserveSeats(customerASeatHold.getSeatHoldId(), "customer.b@example.org"));
    }
}
