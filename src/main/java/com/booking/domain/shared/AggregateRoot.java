package com.booking.domain.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    protected List<DomainEvent> domainEvents = new ArrayList<>();

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(this.domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    protected void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
}
