package org.korolev.autopartsstore.api.category.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class CategoryAlreadyExistsException extends ResourceAlreadyExistsException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
