package org.korolev.automagazine.api.order.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class OrderProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderProductAlreadyExistsException(String message) {
        super(message);
    }
}
