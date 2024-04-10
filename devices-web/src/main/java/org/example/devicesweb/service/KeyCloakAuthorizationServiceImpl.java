package org.example.devicesweb.service;

import org.example.devicesweb.model.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public class KeyCloakAuthorizationServiceImpl implements KeyCloakAuthorizationService {
    @Override
    public String getAuthorizationCode() {
        return "";
    }

    @Override
    public TokenResponse getAccessToken(String code) {
        return null;
    }
}
