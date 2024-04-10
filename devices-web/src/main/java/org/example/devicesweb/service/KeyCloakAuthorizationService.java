package org.example.devicesweb.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.devicesweb.model.TokenResponse;

public interface KeyCloakAuthorizationService {
    String getAuthorizationCode();
    TokenResponse getAccessToken(String code);
}
