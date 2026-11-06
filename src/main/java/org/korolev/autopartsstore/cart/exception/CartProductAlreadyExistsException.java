package org.korolev.autopartsstore.cart.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class CartProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public CartProductAlreadyExistsException(String message) {
        super(message);
    }
}
