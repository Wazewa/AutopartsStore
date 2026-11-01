package org.korolev.autopartsstore.api.kafka.exception;

public class ProducerSendFailedException extends RuntimeException {
    public ProducerSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
