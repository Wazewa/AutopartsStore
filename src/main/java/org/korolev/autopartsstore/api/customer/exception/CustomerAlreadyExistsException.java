package org.korolev.autopartsstore.api.customer.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceAlreadyExistsException;

public class CustomerAlreadyExistsException extends ResourceAlreadyExistsException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
