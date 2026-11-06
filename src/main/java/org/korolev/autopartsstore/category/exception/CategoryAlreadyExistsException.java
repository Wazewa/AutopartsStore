package org.korolev.autopartsstore.category.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class CategoryAlreadyExistsException extends ResourceAlreadyExistsException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
