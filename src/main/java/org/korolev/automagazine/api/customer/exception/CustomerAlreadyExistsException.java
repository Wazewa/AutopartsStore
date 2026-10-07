package org.korolev.automagazine.api.customer.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class CustomerAlreadyExistsException extends ResourceAlreadyExistsException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
