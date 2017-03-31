# Ticket Service

## Interface
The following interface is implemented by this application:
```java
public interface TicketService {
/**
* The number of seats in the venue that are neither held nor reserved
*
* @return the number of tickets available in the venue
*/
  int numSeatsAvailable();
/**
* Find and hold the best available seats for a customer
*
* @param numSeats the number of seats to find and hold
* @param customerEmail unique identifier for the customer
* @return a SeatHold object identifying the specific seats and related
information
*/
  SeatHold findAndHoldSeats(int numSeats, String customerEmail);
/**
* Commit seats held for a specific customer
*
* @param seatHoldId the seat hold identifier
* @param customerEmail the email address of the customer to which the
seat hold is assigned
* @return a reservation confirmation code
*/
String reserveSeats(int seatHoldId, String customerEmail);
}
```

## Assumptions
* Seats are most desirable from lowest number (1) to higher numbers (9999).
* High-demand: does that mean the venue is in high-demand or the seats are? Assume seats so the app should be able to handle many simultaneous requests.
* Do seats need to be held for you to purchase them? Can you purchase non-held but available seats?  I assume that to buy a seat you need to place a hold on it with your customer ID (email address).
* I assume that users do not care about contiguous seats and are okay with "best" seats that can be split.
* Seats must be held before they can be reserved
* I used the provided interface verbatim. I assumed that someone somewhere else might have a copy of this contract so I am locked into providing functionality based on that contract.

## Design considerations
* Seats are held for an arbitrary amount of time. After that they are no longer considered held and revert to available status.
* The reservation confirmation code is a concert identifier + unique value.  The only other code is "INVALID".  This is a way to identify that the reservation attempt failed because the hold is invalid.  This could mean the wrong user tried to book it, the user tried to hold 0 or less seats, or the hold has been previously invalidated (due to being booked). 
* If there are no seats available and a customer tries to hold a seat it will result in an a valid SeatHold object with no seat numbers held.  It is up to the user of the service to determine what to do next.  If the user takes this SeatHold and tries to make a reservation the user will receive the "INVALID" code.
* Used threads to handle load.  Max threads are 10 but can be expanded based on load testing and expected usage.
* The implementation of this is in-memory.  This was done to simplify the implementation but this could be coded to a database or NoSQL store on the back-end via different implementations of the service.
* As soon as a held is place a timer is executed in a separate thread.  If the seat is reserved before the timer goes off the hold is invalidated so it can't be used again.  If the timer goes off and the seat is still in the held status then the seat is made available again.
* Test-driven development was used to develop the meat of the service implementation.  I first wrote tests, let them fail, then steadily built the functionality required until the tests passed.

## To run the app
To run the tests execute:
```
mvn test
```
The main app tries to simulate a typical experience that a back-end request would receive using multithreading and generated users:
* 100 users will try to hold between 1 and 8 seats each. The amount of seats reserved is randomly decided
* A random 50% of the 100 customers will attempt to reserve a hold
* 5 seconds will pass to make the logs a bit easier to read
* 10 more eager customers will try to reserve seats left over from the first run
* A random 50% of the 110 total customers will attempt to reserve a hold

To run the demo execute:
```
mvn compile
mvn exec:java -Dexec.mainClass="io.github.yhippa.TicketServiceDemo" -Djava.util.logging.SimpleFormatter.format='%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n'
```