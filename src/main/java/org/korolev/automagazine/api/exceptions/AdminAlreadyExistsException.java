package org.korolev.automagazine.api.exceptions;

public class AdminAlreadyExistsException extends ResourceAlreadyExistsException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
