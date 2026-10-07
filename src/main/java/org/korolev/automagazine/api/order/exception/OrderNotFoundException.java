package org.korolev.automagazine.api.order.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
