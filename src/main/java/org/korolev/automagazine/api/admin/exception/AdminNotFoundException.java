package org.korolev.automagazine.api.admin.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class AdminNotFoundException extends ResourceNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
