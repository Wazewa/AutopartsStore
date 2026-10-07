package org.korolev.automagazine.api.order.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class OrderAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
