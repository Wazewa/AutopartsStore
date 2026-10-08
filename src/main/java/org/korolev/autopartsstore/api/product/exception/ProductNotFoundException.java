package org.korolev.autopartsstore.api.product.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
