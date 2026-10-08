package org.korolev.autopartsstore.api.order.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class OrderProductNotFoundException extends ResourceNotFoundException {
    public OrderProductNotFoundException(String message) {
        super(message);
    }
}