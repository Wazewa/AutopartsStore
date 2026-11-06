package org.korolev.autopartsstore.order.dto;

import org.korolev.autopartsstore.order.entity.OrderStatus;
import org.korolev.autopartsstore.order.entity.PayMethod;

import java.time.Instant;

public record OrderResponse(
        Long id,
        Instant orderDate,
        OrderStatus status,
        PayMethod payMethod,
        Long userId
) {
}
