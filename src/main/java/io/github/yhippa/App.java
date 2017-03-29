package io.github.yhippa;

import io.github.yhippa.entities.Venue;
import io.github.yhippa.services.TicketService;
import io.github.yhippa.services.TicketServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        TicketService ticketService = new TicketServiceImpl();
        Venue jiffyLubeLive = new Venue(10, "Jiffy Lube Live");
    }
}
