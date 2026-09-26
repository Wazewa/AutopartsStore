package org.korolev.automagazine.api.exceptions;

public class OrderNotModifiableException extends RuntimeException {
    public OrderNotModifiableException(String message) {
        super(message);
    }
}
