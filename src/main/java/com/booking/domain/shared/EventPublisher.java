package com.booking.domain.shared;

public interface EventPublisher {
    void publish(DomainEvent event);

    void subscribe(Class <? extends DomainEvent> eventType, DomainEventListener listener);
}
