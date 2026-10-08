package org.korolev.autopartsstore.api.order.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceAlreadyExistsException;

public class OrderProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderProductAlreadyExistsException(String message) {
        super(message);
    }
}
