package org.korolev.automagazine.api.cart.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class CartProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartProductAlreadyExistsException(String message) {
        super(message);
    }
}
