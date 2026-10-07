package org.korolev.automagazine.api.order.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class OrderProductNotFoundException extends ResourceNotFoundException {
    public OrderProductNotFoundException(String message) {
        super(message);
    }
}