package org.korolev.automagazine.api.category.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
