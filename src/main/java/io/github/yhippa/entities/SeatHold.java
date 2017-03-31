package io.github.yhippa.entities;

import io.github.yhippa.enums.Validity;

import java.util.List;

public class SeatHold {
    private static int idGenerator = 1;
    private int seatHoldId;
    private List<Integer> seatNumbersHeld;
    private String emailAddress;
    private Validity validity;

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
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

    public SeatHold(List<Integer> seatNumbersHeld, String emailAddress) {
        seatHoldId = idGenerator++;
        this.seatNumbersHeld = seatNumbersHeld;
        this.emailAddress = emailAddress;
        this.validity = Validity.VALID;
    }

    @Override
    public String toString() {
        return "SeatHold{" +
                "seatHoldId=" + seatHoldId +
                ", seatNumbersHeld=" + seatNumbersHeld +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    public Validity getValidity() {
        return validity;
    }

    public void setValidity(Validity validity) {
        this.validity = validity;
    }
}
