package org.korolev.autopartsstore.order.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class OrderProductNotFoundException extends ResourceNotFoundException {
    public OrderProductNotFoundException(String message) {
        super(message);
    }
}