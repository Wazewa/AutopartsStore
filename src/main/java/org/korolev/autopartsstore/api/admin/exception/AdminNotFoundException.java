package org.korolev.autopartsstore.api.admin.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class AdminNotFoundException extends ResourceNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
