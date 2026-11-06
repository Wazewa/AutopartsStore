package org.korolev.autopartsstore.order.dto;

import org.korolev.autopartsstore.order.entity.OrderStatus;

import java.time.Instant;

public record OrderFilter(
        OrderStatus status,
        Instant dateFrom,
        Instant dateTo
) {
}
