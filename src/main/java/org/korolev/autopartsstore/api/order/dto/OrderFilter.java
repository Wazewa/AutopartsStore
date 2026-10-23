package org.korolev.autopartsstore.api.order.dto;

import org.korolev.autopartsstore.api.order.entity.OrderStatus;

import java.time.Instant;

public record OrderFilter(
        OrderStatus status,
        Instant dateFrom,
        Instant dateTo
) {
}
