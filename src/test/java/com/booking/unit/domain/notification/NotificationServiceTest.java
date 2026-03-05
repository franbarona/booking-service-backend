package com.booking.unit.domain.notification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.booking.domain.booking.Booking;
import com.booking.domain.booking.ReservationCreatedEvent;
import com.booking.domain.notification.NotificationService;
import com.booking.domain.shared.EventPublisher;
import com.booking.infrastructure.messaging.InMemoryEventPublisher;

public class NotificationServiceTest {

    @Test
    void shouldSendNotificationWhenReservationIsCreated() {
        // Arrange
        EventPublisher publisher =  new InMemoryEventPublisher();
        NotificationService notificationService = new NotificationService(publisher);

        String guestName = "John Doe";
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");

        // Act
        Booking booking = new Booking(guestName, checkIn, checkOut, pricePerNight);
        ReservationCreatedEvent event = (ReservationCreatedEvent) booking.getDomainEvents().get(0);
        publisher.publish(event);

        // Assert
        assertTrue(notificationService.wasNotified());
        assertEquals(guestName, notificationService.getLastNotifiedGuest());
    }
    
}
