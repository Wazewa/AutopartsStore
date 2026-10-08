package org.korolev.autopartsstore.api.order.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
