package org.korolev.automagazine.api.exceptions;

public class OrderProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderProductAlreadyExistsException(String message) {
        super(message);
    }
}
