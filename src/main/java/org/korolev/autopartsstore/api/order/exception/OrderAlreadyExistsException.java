package org.korolev.autopartsstore.api.order.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class OrderAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
