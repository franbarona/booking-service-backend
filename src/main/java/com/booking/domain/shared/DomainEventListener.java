package com.booking.domain.shared;

public interface DomainEventListener {
    void handle(DomainEvent event);

    boolean supports(Class<? extends DomainEvent> eventType);
}
