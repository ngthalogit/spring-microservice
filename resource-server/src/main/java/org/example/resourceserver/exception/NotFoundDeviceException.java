package org.example.resourceserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundDeviceException extends Exception {
    public NotFoundDeviceException(String message) {
        super(message);
    }
}
