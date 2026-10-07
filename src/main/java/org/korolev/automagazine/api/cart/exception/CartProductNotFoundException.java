package org.korolev.automagazine.api.cart.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class CartProductNotFoundException extends ResourceNotFoundException {
    public CartProductNotFoundException(String message) {
        super(message);
    }
}
