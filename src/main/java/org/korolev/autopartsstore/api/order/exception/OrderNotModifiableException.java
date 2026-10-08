package org.korolev.autopartsstore.api.order.exception;

public class OrderNotModifiableException extends RuntimeException {
    public OrderNotModifiableException(String message) {
        super(message);
    }
}
