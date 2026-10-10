package org.korolev.autopartsstore.api.product.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class ProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
