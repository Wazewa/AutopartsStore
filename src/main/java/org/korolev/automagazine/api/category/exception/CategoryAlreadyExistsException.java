package org.korolev.automagazine.api.category.exception;

import org.korolev.automagazine.api.exceptions.ResourceAlreadyExistsException;

public class CategoryAlreadyExistsException extends ResourceAlreadyExistsException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
