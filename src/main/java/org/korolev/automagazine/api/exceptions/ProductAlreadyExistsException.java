package org.korolev.automagazine.api.exceptions;

public class ProductAlreadyExistsException extends ResourceAlreadyExistsException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
