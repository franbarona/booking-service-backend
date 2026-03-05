package com.booking.infrastructure.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.domain.shared.DomainEvent;
import com.booking.domain.shared.DomainEventListener;
import com.booking.domain.shared.EventPublisher;

public class InMemoryEventPublisher implements EventPublisher {

    private final Map<Class<? extends DomainEvent>, List<DomainEventListener>> listeners = new HashMap<>();

    // No constructor needed for the moment

    // Publish events to listeners
    @Override
    public void publish(DomainEvent event) {
        // List<DomainEventListener> eventListeners =
        // listeners.getOrDefault(event.getClass(), new ArrayList<>());

        // eventListeners.forEach(listener -> {
        // if (listener.supports(event.getClass())) {
        // listener.handle(event);
        // }
        // });
        listeners.getOrDefault(event.getClass(), new ArrayList<>()).stream()
                .filter(listener -> listener.supports(event.getClass()))
                .forEach(listener -> listener.handle(event));
    }

    // Subscribe listeners to events
    @Override
    public void subscribe(Class<? extends DomainEvent> eventType, DomainEventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        listeners.get(eventType).add(listener);
    }
}
