package org.korolev.automagazine.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CartProductAlreadyExistsException extends RuntimeException {
    public CartProductAlreadyExistsException(String message) {
        super(message);
    }
}
