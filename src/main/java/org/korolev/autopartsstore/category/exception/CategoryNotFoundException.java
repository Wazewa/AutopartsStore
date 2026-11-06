package org.korolev.autopartsstore.category.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
