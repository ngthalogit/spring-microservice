package org.example.resourceserver.exception;

import org.apache.coyote.BadRequestException;
import org.example.resourceserver.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Date;


@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        String message = "Internal Server Error: " + e.getMessage();
        return ResponseEntity.internalServerError().body(
                new ErrorResponse(Date.from(Instant.now()), HttpStatus.INTERNAL_SERVER_ERROR.value(), message)
        );
    }

    @ExceptionHandler(NotFoundDeviceException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundDeviceException(NotFoundDeviceException e) {
        String message = "Not found device with id: " + e.getMessage();
        return new ResponseEntity<>(new ErrorResponse(Date.from(Instant.now()), HttpStatus.NOT_FOUND.value(), message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingDeviceException.class)
    public ResponseEntity<ErrorResponse> handleExistingDeviceException(ExistingDeviceException e) {
        String message = "Device with id: " + e.getMessage() + " already exists";
        return ResponseEntity.badRequest().body(new ErrorResponse(Date.from(Instant.now()), HttpStatus.BAD_REQUEST.value(), message));
    }
}
