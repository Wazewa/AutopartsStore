package org.korolev.automagazine.api.product.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
