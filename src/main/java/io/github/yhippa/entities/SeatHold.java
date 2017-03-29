package io.github.yhippa.entities;

import java.util.List;

/**
 * Created by richardyhip on 3/28/17.
 */
public class SeatHold {
    private static long idGenerator = 0;
    private long seatHoldId;

    public long getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(long seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public List<Integer> getSeatNumbersHeld() {
        return seatNumbersHeld;
    }

    public void setSeatNumbersHeld(List<Integer> seatNumbersHeld) {
        this.seatNumbersHeld = seatNumbersHeld;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    private List<Integer> seatNumbersHeld;
    private String emailAddress;

    public SeatHold(List<Integer> seatNumbersHeld, String emailAddress) {
        seatHoldId = idGenerator++;
        this.seatNumbersHeld = seatNumbersHeld;
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "SeatHold{" +
                "seatHoldId=" + seatHoldId +
                ", seatNumbersHeld=" + seatNumbersHeld +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
