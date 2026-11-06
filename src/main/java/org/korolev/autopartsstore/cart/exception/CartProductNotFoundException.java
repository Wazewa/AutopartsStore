package org.korolev.autopartsstore.cart.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class CartProductNotFoundException extends ResourceNotFoundException {
    public CartProductNotFoundException(String message) {
        super(message);
    }
}
