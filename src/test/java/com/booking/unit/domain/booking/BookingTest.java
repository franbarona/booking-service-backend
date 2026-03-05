package com.booking.unit.domain.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.booking.domain.booking.Booking;
import com.booking.domain.booking.ReservationCreatedEvent;
import com.booking.domain.notification.NotificationService;
import com.booking.domain.shared.DomainEvent;
import com.booking.domain.shared.EventPublisher;
import com.booking.infrastructure.messaging.InMemoryEventPublisher;

public class BookingTest {

    @Test
    void shouldCreateReservationWithValidDates() {
        // Arrange
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2026, 3, 10);
        LocalDate checkOut = LocalDate.of(2026, 3, 12);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        // Act
        Booking booking = new Booking(guestName, checkIn, checkOut, pricePerNight);
        // Assert
        assertEquals(guestName, booking.getGuestName());
        assertEquals(checkIn, booking.getCheckIn());
        assertEquals(checkOut, booking.getCheckOut());
    }

    @Test
    void shouldNotAllowCheckOutBeforeCheckIn() {
        // Arrange
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2026, 3, 10);
        LocalDate checkOut = LocalDate.of(2026, 3, 9);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Booking(guestName, checkIn, checkOut, pricePerNight);
        });
        assertEquals("Check-out date must be after check-in date", exception.getMessage());
    }

    @Test
    void shouldNotAllowBookingInThePast() {
        // Arrange
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2020, 1, 1);
        LocalDate checkOut = LocalDate.of(2020, 1, 5);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Booking(guestName, checkIn, checkOut, pricePerNight);
        });
        assertEquals("Check-in date cannot be in the past", exception.getMessage());
    }

    @Test
    void shouldEmitReservationCreatedEventOnCreation() {
        // Arrange
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 12);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        // Act
        Booking booking = new Booking(guestName, checkIn, checkOut, pricePerNight);
        List<DomainEvent> events = booking.getDomainEvents();

        // Assert
        assertEquals(1, events.size());
        assertTrue(events.get(0) instanceof ReservationCreatedEvent);
        ReservationCreatedEvent event = (ReservationCreatedEvent) events.get(0);
        assertEquals(booking.getBookingId(), event.getBookingId());
        assertEquals(guestName, event.getGuestName());
    }

    @Test
    void shouldReturnTotalPrice() {
        // Arrange
        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");

        // Act
        Booking booking = new Booking(guestName, checkIn, checkOut, pricePerNight);

        // Assert
        assertEquals(new BigDecimal("300.00"), booking.getTotalPrice());
    }

    @Test
    void shouldPublishReservationCreatedEventWhenCreated() {
        // Arrange
        EventPublisher publisher = new InMemoryEventPublisher();
        NotificationService notificationService = new NotificationService(publisher);

        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");

        // Act
        new Booking(guestName, checkIn, checkOut, pricePerNight, publisher);

        // Assert
        assertTrue(notificationService.wasNotified());
        assertEquals(guestName, notificationService.getLastNotifiedGuest());
    }
}
