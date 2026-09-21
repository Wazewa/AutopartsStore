package org.korolev.automagazine.api.exceptions;

public class CartAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
