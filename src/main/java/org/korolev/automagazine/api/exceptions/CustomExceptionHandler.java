package org.korolev.automagazine.api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.korolev.automagazine.api.dto.ErrorResponse;
import org.korolev.automagazine.api.dto.ValidationErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         HttpServletRequest httpServletRequest) {

        return createResponseEntity(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex,
                                                                              HttpServletRequest httpServletRequest) {
        return createResponseEntity(HttpStatus.CONFLICT, "Already exists",
                ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ErrorResponse> handleCategoryInUseException(CategoryInUseException ex,
                                                                              HttpServletRequest httpServletRequest) {
        return createResponseEntity(HttpStatus.CONFLICT, "Conflict",
                ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                               HttpServletRequest httpServletRequest) {
        return createResponseEntity(HttpStatus.CONFLICT, "Already exists",
                "Resource already exists or violates data integrity constraints",
                Instant.now(), httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                        HttpServletRequest httpServletRequest) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, "Bad request",
                ex.getMessage(), Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                            HttpServletRequest httpServletRequest) {

        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return createResponseEntity(HttpStatus.BAD_REQUEST, "Bad request",
                message, Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationErrorResponse.of(400, "Validation failed",
                        "Error in " + fieldErrors.size() + " fields",
                        Instant.now(), httpServletRequest.getRequestURI(), fieldErrors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest httpServletRequest) {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",
                "An unexpected error occurred. Please contact support", Instant.now(),
                httpServletRequest.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(HttpStatus httpStatus, String error,
                                                               String message, Instant timestamp, String request) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.of(httpStatus.value(), error, message,
                        timestamp, request));
    }
}
