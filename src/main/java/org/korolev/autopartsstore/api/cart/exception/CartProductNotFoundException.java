package org.korolev.autopartsstore.api.cart.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class CartProductNotFoundException extends ResourceNotFoundException {
    public CartProductNotFoundException(String message) {
        super(message);
    }
}
