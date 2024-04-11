package org.example.devicesweb.service;

import org.example.devicesweb.model.TokenResponse;
import org.springframework.web.servlet.view.RedirectView;

public interface KeyCloakAuthorizationService {
    RedirectView directAuthorizationCodeView();
    TokenResponse getAccessToken(String code);
}
