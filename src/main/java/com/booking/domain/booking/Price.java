package com.booking.domain.booking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Price {

    private final BigDecimal pricePerNight;
    private final long numberOfNights;
    private BigDecimal total;
    private boolean discountApplied;

    public Price(LocalDate checkIn, LocalDate checkOut, BigDecimal pricePerNight) {
        if (pricePerNight.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative");

        }
        this.numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.pricePerNight = pricePerNight;
        this.total = pricePerNight.multiply(BigDecimal.valueOf(numberOfNights)).setScale(2, RoundingMode.HALF_UP);
        this.discountApplied = false;
    }

    public void applyDiscount(BigDecimal discountPercentage) {
        if (discountApplied) {
            throw new IllegalArgumentException("Discount already applied");
        }

        if (discountPercentage.compareTo(new BigDecimal("100.0")) > 0) {
            throw new IllegalArgumentException("Discount cannot be greater than 100%");
        }

        this.discountApplied = true;
        BigDecimal discountAmount = total.multiply(discountPercentage).divide(new BigDecimal(100));
        this.total = this.total.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal pricePerNight() {
        return this.pricePerNight;
    }

    public BigDecimal getTotal() {
        return this.total;
    }
}
