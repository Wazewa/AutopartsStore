package org.korolev.autopartsstore.order.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class OrderProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderProductAlreadyExistsException(String message) {
        super(message);
    }
}
