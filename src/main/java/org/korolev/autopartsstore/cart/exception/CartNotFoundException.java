package org.korolev.autopartsstore.cart.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
