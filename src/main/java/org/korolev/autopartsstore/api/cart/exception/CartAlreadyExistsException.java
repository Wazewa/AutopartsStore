package org.korolev.autopartsstore.api.cart.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceAlreadyExistsException;

public class CartAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
