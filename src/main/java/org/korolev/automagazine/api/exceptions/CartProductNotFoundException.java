package org.korolev.automagazine.api.exceptions;

public class CartProductNotFoundException extends ResourceNotFoundException {
    public CartProductNotFoundException(String message) {
        super(message);
    }
}
