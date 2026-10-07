package org.korolev.automagazine.api.cart.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class CartAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
