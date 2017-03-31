package io.github.yhippa.entities;

import io.github.yhippa.enums.HoldStatus;

import static io.github.yhippa.enums.HoldStatus.AVAILABLE;

public class Seat {
    private HoldStatus availability;
    private int seatHoldId;

    public Seat() {
        availability = AVAILABLE;
    }

    public HoldStatus getAvailability() {
        return availability;
    }

    public void setAvailability(HoldStatus availability) {
        this.availability = availability;
    }

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "availability=" + availability +
                ", seatHoldId=" + seatHoldId +
                '}';
    }
}
