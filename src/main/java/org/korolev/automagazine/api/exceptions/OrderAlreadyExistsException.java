package org.korolev.automagazine.api.exceptions;

public class OrderAlreadyExistsException extends ResourceAlreadyExistsException {
    public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
