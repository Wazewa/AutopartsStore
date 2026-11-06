package org.korolev.autopartsstore.product.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class ProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
