package org.korolev.automagazine.api.product.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class ProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
