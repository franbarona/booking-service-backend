package com.booking.domain.booking;

import java.util.UUID;

public class BookingId {
    private final UUID value;
    
    public BookingId() {
        this.value = UUID.randomUUID();
    }
    
    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingId bookingId = (BookingId) o;
        return value.equals(bookingId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
