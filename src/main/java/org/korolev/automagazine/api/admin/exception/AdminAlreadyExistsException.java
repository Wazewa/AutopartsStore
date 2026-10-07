package org.korolev.automagazine.api.admin.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class AdminAlreadyExistsException extends ResourceAlreadyExistsException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
