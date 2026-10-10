package org.korolev.autopartsstore.api.admin.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class AdminAlreadyExistsException extends ResourceAlreadyExistsException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
