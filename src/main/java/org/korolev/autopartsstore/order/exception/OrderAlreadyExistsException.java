package org.korolev.autopartsstore.order.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class OrderAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
