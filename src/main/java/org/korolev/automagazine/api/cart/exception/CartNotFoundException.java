package org.korolev.automagazine.api.cart.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
