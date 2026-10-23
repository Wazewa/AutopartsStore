package org.korolev.autopartsstore.api.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderEvent {
    START_PROCESSING(OrderStatus.PROCESSING),
    READY_FOR_PICKUP(OrderStatus.READY_FOR_PICKUP),
    COMPLETE(OrderStatus.COMPLETED),
    CANCEL(OrderStatus.CANCELED),
    RETURN(OrderStatus.RETURNED);

    private final OrderStatus targetStatus;
}