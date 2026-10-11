package org.korolev.autopartsstore.api.cart.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class CartProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartProductAlreadyExistsException(String message) {
        super(message);
    }
}
