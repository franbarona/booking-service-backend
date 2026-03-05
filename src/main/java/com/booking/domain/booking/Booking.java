package com.booking.domain.booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.booking.domain.shared.AggregateRoot;
import com.booking.domain.shared.EventPublisher;

public class Booking extends AggregateRoot {
    private final BookingId bookingId;
    private final String guestName;
    private final DateRange dateRange;
    private final Price price;

    public Booking(String guestName, LocalDate checkIn, LocalDate checkOut, BigDecimal pricePerNight) {
        this(guestName, checkIn, checkOut, pricePerNight, null);
    }

    public Booking(String guestName, LocalDate checkIn, LocalDate checkOut, BigDecimal pricePerNight,
            EventPublisher publisher) {
        if (guestName == null || guestName.isBlank()) {
            throw new IllegalArgumentException("Guest name cannot be empty");
        }
        this.bookingId = new BookingId();
        this.guestName = guestName;
        this.dateRange = new DateRange(checkIn, checkOut);
        this.price = new Price(checkIn, checkOut, pricePerNight);
        ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent(bookingId, guestName, checkIn,
                checkOut);
        addDomainEvent(reservationCreatedEvent);
        if (publisher != null) {
            publisher.publish(reservationCreatedEvent);
        }
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckIn() {
        return dateRange.getCheckIn();
    }

    public LocalDate getCheckOut() {
        return dateRange.getCheckOut();
    }

    public BigDecimal getTotalPrice() {
        return price.getTotal();
    }

    public void applyDiscount(BigDecimal discountPercentage) {
        this.price.applyDiscount(discountPercentage);
        // Fase 2: addDomainEvent(new DiscountAppliedEvent(...))
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Booking booking = (Booking) o;
        return bookingId.equals(booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}
