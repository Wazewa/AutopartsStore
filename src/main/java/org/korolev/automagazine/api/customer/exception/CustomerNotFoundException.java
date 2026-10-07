package org.korolev.automagazine.api.customer.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class CustomerNotFoundException extends ResourceNotFoundException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
