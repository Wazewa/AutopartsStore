package org.korolev.automagazine.api.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.korolev.automagazine.api.category.exception.CategoryInUseException;
import org.korolev.automagazine.api.dto.ErrorResponse;
import org.korolev.automagazine.api.dto.ValidationErrorResponse;
import org.korolev.automagazine.api.order.exception.OrderNotModifiableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         HttpServletRequest httpServletRequest) {
        log.warn("Resource not found: {}", ex.getMessage());
        return createResponseEntity(HttpStatus.NOT_FOUND, "Not found", ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex,
                                                                              HttpServletRequest httpServletRequest) {
        log.warn("Resource already exists: {}", ex.getMessage());
        return createResponseEntity(HttpStatus.CONFLICT, "Already exists",
                ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(CategoryInUseException.class)
    public ResponseEntity<ErrorResponse> handleCategoryInUseException(CategoryInUseException ex,
                                                                              HttpServletRequest httpServletRequest) {
        log.warn("Resource conflict: {}", ex.getMessage());
        return createResponseEntity(HttpStatus.CONFLICT, "Conflict",
                ex.getMessage(),
                Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                               HttpServletRequest httpServletRequest) {
        log.warn("Resource conflict at {}: {}", httpServletRequest.getRequestURI(), ex.getMessage());
        return createResponseEntity(HttpStatus.CONFLICT, "Already exists",
                "Resource already exists or violates data integrity constraints",
                Instant.now(), httpServletRequest.getRequestURI());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                        HttpServletRequest httpServletRequest) {
        log.warn("Bad request at: {}", httpServletRequest.getRequestURI());
        return createResponseEntity(HttpStatus.BAD_REQUEST, "Bad request",
                ex.getMessage(), Instant.now(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex,
                                                                            HttpServletRequest httpServletRequest) {

        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.warn("Constraint violation at {}: {}", httpServletRequest.getRequestURI(), message);
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
        log.warn("Validation failed at {}: {}", httpServletRequest.getRequestURI(), fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationErrorResponse.of(400, "Validation failed",
                        "Error in " + fieldErrors.size() + " fields",
                        Instant.now(), httpServletRequest.getRequestURI(), fieldErrors));
    }

    @ExceptionHandler(OrderNotModifiableException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotModifiable(
            OrderNotModifiableException ex, HttpServletRequest request) {
        log.warn("Order not modifiable: {}", ex.getMessage());
        return createResponseEntity(HttpStatus.CONFLICT, "Conflict",
                ex.getMessage(), Instant.now(), request.getRequestURI());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpServletRequest httpServletRequest) {
        log.warn("Endpoint not found: {}", httpServletRequest.getRequestURI());
        return createResponseEntity(HttpStatus.NOT_FOUND, "Not found",
                "Endpoint not found: " + httpServletRequest.getRequestURI(),
                Instant.now(), httpServletRequest.getRequestURI());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest httpServletRequest) {
        log.error("Internal server error: {}", httpServletRequest.getRequestURI(), ex);
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
