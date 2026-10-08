package org.korolev.autopartsstore.api.category.exception;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(String message) {
        super(message);
    }
}
