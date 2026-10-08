package org.korolev.autopartsstore.api.customer.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class CustomerNotFoundException extends ResourceNotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
