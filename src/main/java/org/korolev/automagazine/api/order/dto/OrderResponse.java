package org.korolev.automagazine.api.order.dto;

import org.korolev.automagazine.api.order.entity.OrderStatus;
import org.korolev.automagazine.api.order.entity.PayMethod;

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
