# Ticket Service
##Nouns
* Ticket service
  * Discovery
  * Temporary hold
  * Final reservation
* Seat
  * SeatHold
  * id
* SeatHold
* Venue
  * List<Seats>
* Customer
  * email

##Interface
* TicketService
  * Int numSeatsAvailable
  * SeatHold findAndHoldSeats
  * reserveSeats

##Assumptions
* Seats are most desirable from lowest number (1) to higher numbers (9999)
* High-demand...does that mean the venue is in high demand or the seats are?  Assume seats
* Seats are held for an arbitrary amount of time. After that they are no longer considered held.
* Do seats need to be held for you to purchase them?  Can you purchase non-held but available seats?
* assume that users do not care about contiguous seats and are okay with "best" seats that can be split

##Use cases
* Find available seats
* Hold a seat
* Reserve a seat

##Logic flow
* User wants 4 tickets
* Query to see how many tickets available (50)
* put a hold on 4 best available tickets
* Customer wants to reserve the held tickets
  * Get the hold (contains specific seats to hold)
  * check to see if the seats are held for that customer
  * if so change seats to reserved for that customer

##Examples
I want to get seats for the concert this weekend
Me, my girlfriend, and another couple want to go
Check to see if there are 4 seats available
If no, done
If yes, identify which seats are available for me and hold them
I have 60 seconds to decide if I want to reserve them or not
If I reserve the seats prior to 60 seconds then they’re mine.  Success!  (what happens if i try to reserve those same seats again or try to reserve a set of seats that don’t belong to me?)
If I reserve the seats after 60 seconds then I am unable to do that and must hold seats again.
If I don’t do anything after 60 seconds then the seats go from held to available.

##App Simulation
* 100 users reserve between 1 and 8 seats each (random amount)
* They all get holds
* Some of them try to reserve after a random amount of time based on their holdid