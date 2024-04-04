package org.example.resourceserver.model;

import java.util.Date;

public record ErrorResponse(Date time, int status, String error) {
}
