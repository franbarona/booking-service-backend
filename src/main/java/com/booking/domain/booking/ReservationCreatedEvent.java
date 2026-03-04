package com.booking.domain.booking;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.booking.domain.shared.DomainEvent;

public class ReservationCreatedEvent implements DomainEvent {
    private final BookingId bookingId;
    private final String guestName;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final Date timestamp;

    public ReservationCreatedEvent(BookingId bookingId, String guestName, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = Objects.requireNonNull(bookingId);
        this.guestName = Objects.requireNonNull(guestName);
        this.checkIn = Objects.requireNonNull(checkIn);
        this.checkOut = Objects.requireNonNull(checkOut);
        this.timestamp = new Date();
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
