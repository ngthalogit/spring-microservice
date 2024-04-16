package org.example.devicesweb.exception;

public class UnAuthorizedAccessToken extends RuntimeException {
    public UnAuthorizedAccessToken(String message) {
        super(message);
    }
}
