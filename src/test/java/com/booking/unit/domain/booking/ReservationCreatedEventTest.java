package com.booking.unit.domain.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.booking.domain.booking.BookingId;
import com.booking.domain.booking.ReservationCreatedEvent;

public class ReservationCreatedEventTest {

    @Test
    void shouldCreateEventWithReservationData() {
        // Arrange
        BookingId bookingId = new BookingId();
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 12);

        // Act
        // TODO: create event with the reservation data
        ReservationCreatedEvent event = new ReservationCreatedEvent(bookingId, guestName, checkIn, checkOut);

        // Assert
        // - Check that bookingId is correct
        assertEquals(bookingId, event.getBookingId());
        // - Check that guestName is correct
        assertEquals(guestName, event.getGuestName());
        // - Check that checkIn is correct
        assertEquals(checkIn, event.getCheckIn());
        // - Check that checkOut is correct
        assertEquals(checkOut, event.getCheckOut());
    }

    @Test
    void shouldHaveEventOcurredTimestamp() {
        // Arrange
        BookingId bookingId = new BookingId();
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 12);
        ReservationCreatedEvent event = new ReservationCreatedEvent(bookingId, guestName, checkIn, checkOut);

        // Act & Assert
        // - Timestamp should not be null
        // - Timestamp should be close to now (within 1 second)
        assertNotNull(event.getTimestamp());
        long now = System.currentTimeMillis();
        long eventTime = event.getTimestamp().getTime();
        assertTrue(Math.abs(now - eventTime) < 1000);
    }
}
