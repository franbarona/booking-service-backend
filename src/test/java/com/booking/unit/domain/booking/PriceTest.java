package com.booking.unit.domain.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.booking.domain.booking.Price;

public class PriceTest {

    @Test
    void shouldCalculateBasePriceForMultipleNights() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13); // 3 nights
        BigDecimal pricePerNight = new BigDecimal("100.0");
        // Act
        Price price = new Price(checkIn, checkOut, pricePerNight);
        // Assert
        assertEquals(new BigDecimal("300.00"), price.getTotal());
    }

    @Test
    void shouldApplyDiscountPercentage() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        BigDecimal discountPercentage = new BigDecimal("10.0");

        // Act
        Price price = new Price(checkIn, checkOut, pricePerNight);
        price.applyDiscount(discountPercentage);

        // Asserts
        assertEquals(new BigDecimal("270.00"), price.getTotal());
    }

    @Test
    void shouldNotAllowNegativeBasePrice() {
        // Arrange & Act
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Price(LocalDate.of(2027, 3, 10), LocalDate.of(2027, 3, 13), new BigDecimal("-100.0")));
        // Asserts
        assertEquals("Price per night cannot be negative", exception.getMessage());
    }

    @Test
    void shouldNotAllowNegativeTotalPrice() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        BigDecimal discountPercentage = new BigDecimal("105.0");

        // Act
        Price price = new Price(checkIn, checkOut, pricePerNight);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> price.applyDiscount(discountPercentage));

        // Asserts
        assertEquals("Discount cannot be greater than 100%", exception.getMessage());
    }

    @Test
    void shouldNotAllowMultipleDiscounts() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2027, 3, 10);
        LocalDate checkOut = LocalDate.of(2027, 3, 13);
        BigDecimal pricePerNight = new BigDecimal("100.0");
        BigDecimal discountPercentage = new BigDecimal("10.0");

        // Act
        Price price = new Price(checkIn, checkOut, pricePerNight);
        price.applyDiscount(discountPercentage);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> price.applyDiscount(new BigDecimal("5.0")));

        // Asserts
        assertEquals("Discount already applied", exception.getMessage());
    }
}
