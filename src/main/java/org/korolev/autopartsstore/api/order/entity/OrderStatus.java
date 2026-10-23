package org.korolev.autopartsstore.api.order.entity;

import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    READY_FOR_PICKUP,
    COMPLETED,
    CANCELED,
    RETURNED;

    public static final Map<OrderStatus, Set<OrderStatus>> TRANSITIONS = Map.of(
            PENDING, Set.of(PROCESSING, CANCELED),
            PROCESSING, Set.of(READY_FOR_PICKUP, CANCELED),
            READY_FOR_PICKUP, Set.of(COMPLETED, CANCELED),
            COMPLETED, Set.of(RETURNED),
            CANCELED, Set.of(),
            RETURNED, Set.of()
    );

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return TRANSITIONS.getOrDefault(this, Set.of()).contains(nextStatus);
    }
}
