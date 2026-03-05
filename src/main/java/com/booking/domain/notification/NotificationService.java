package com.booking.domain.notification;

import com.booking.domain.booking.ReservationCreatedEvent;
import com.booking.domain.shared.DomainEvent;
import com.booking.domain.shared.DomainEventListener;
import com.booking.domain.shared.EventPublisher;

public class NotificationService implements DomainEventListener {
    private final EventPublisher eventPublisher;
    private String lastNotifiedGuest;
    private boolean notified = false;

    public NotificationService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        eventPublisher.subscribe(ReservationCreatedEvent.class, this);
    }

    @Override
    public void handle(DomainEvent event) {
        if (!(event instanceof ReservationCreatedEvent)) {
            return;
        }

        ReservationCreatedEvent reservationCreatedEvent  = (ReservationCreatedEvent) event;
        String guestName = reservationCreatedEvent.getGuestName();
        this.lastNotifiedGuest = guestName;
        this.notified = true;
    }

    @Override
    public boolean supports(Class<? extends DomainEvent> eventType) {
        return eventType == ReservationCreatedEvent.class;
    }

    public boolean wasNotified() {
        return notified;
    }

    public String getLastNotifiedGuest() {
        return lastNotifiedGuest;
    }
}
