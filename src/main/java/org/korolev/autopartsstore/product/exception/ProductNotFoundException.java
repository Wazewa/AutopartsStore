package org.korolev.autopartsstore.product.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
