package org.korolev.automagazine.api.exceptions;

public class CustomerAlreadyExistsException extends ResourceAlreadyExistsException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
