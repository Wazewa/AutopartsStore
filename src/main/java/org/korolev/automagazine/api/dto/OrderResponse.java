package org.korolev.automagazine.api.dto;

import org.korolev.automagazine.api.entities.OrderStatus;
import org.korolev.automagazine.api.entities.PayMethod;

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
