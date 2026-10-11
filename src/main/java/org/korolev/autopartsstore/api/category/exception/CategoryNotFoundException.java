package org.korolev.autopartsstore.api.category.exception;

import org.korolev.autopartsstore.api.exception.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
