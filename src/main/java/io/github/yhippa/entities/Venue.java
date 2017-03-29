package io.github.yhippa.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richardyhip on 3/28/17.
 */
public class Venue {
    private String name;
    private int capacity; // 288 = 32 seats by 9 rows
    private List<Seat> seatingChart;

    public Venue(int capacity, String name) {
        this.name = name;
        this.capacity = capacity;
        seatingChart = new ArrayList<Seat>();
        SeatHold seatHold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
