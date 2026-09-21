package org.korolev.automagazine.api.exceptions;

public class CartProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartProductAlreadyExistsException(String message) {
        super(message);
    }
}
