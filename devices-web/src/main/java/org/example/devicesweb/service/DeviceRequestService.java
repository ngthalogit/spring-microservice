package org.example.devicesweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.devicesweb.model.TokenResponse;

public interface DeviceRequestService {
    void request(TokenResponse token) throws RuntimeException, JsonProcessingException;
}
