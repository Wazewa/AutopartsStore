package org.korolev.autopartsstore.api.exception;

import org.junit.jupiter.api.Test;
import org.korolev.autopartsstore.api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler handler = new CustomExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    void handleResourceNotFoundException_shouldReturn404() {
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFoundException(
                new ResourceNotFoundException("Not found"), request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().message()).isEqualTo("Not found");
    }

    @Test
    void handleIllegalArgumentException_shouldReturn400() {
        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("Bad request"), request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleException_shouldReturn500() {
        ResponseEntity<ErrorResponse> response = handler.handleException(
                new RuntimeException("Unknown error"), request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}