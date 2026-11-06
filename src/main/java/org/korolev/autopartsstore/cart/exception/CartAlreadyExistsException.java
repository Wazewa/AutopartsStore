package org.korolev.autopartsstore.cart.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class CartAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartAlreadyExistsException(String message) {
        super(message);
    }
}
