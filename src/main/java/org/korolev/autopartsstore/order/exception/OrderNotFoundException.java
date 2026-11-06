package org.korolev.autopartsstore.order.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
