package org.korolev.autopartsstore.api.order.dto;

import org.korolev.autopartsstore.api.order.entity.OrderStatus;
import org.korolev.autopartsstore.api.order.entity.PayMethod;

import java.time.Instant;

public record OrderResponse(
        Long id,
        Instant orderDate,
        OrderStatus status,
        PayMethod payMethod,
        Long customerId,
        Long adminId
) {
}
