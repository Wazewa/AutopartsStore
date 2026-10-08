package org.korolev.autopartsstore.api.cart.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class CartNotFoundException extends ResourceNotFoundException {
    public CartNotFoundException(String message) {
        super(message);
    }
}
