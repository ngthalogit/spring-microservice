package org.example.devicesweb.controller;

import org.example.devicesweb.config.KeyCloakConfigurationProperties;
import org.example.devicesweb.model.TokenResponse;
import org.example.devicesweb.service.KeyCloakAuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import static org.example.devicesweb.constant.KeyCloakTypeRequestProperties.AUTHORIZATION_CODE;


@Controller
@RequestMapping("/oauth2/grant/")
public class KeyCloakAuthorizationController {
    private final KeyCloakAuthorizationService keyCloakAuthorizationService;
    private final KeyCloakConfigurationProperties keyCloakConfigurationProperties;

    public KeyCloakAuthorizationController(KeyCloakAuthorizationService keyCloakAuthorizationService, KeyCloakConfigurationProperties keyCloakConfigurationProperties) {
        this.keyCloakAuthorizationService = keyCloakAuthorizationService;
        this.keyCloakConfigurationProperties = keyCloakConfigurationProperties;
    }

    @GetMapping("/auth")
    public RedirectView auth() {
        return keyCloakAuthorizationService.directAuthorizationCodeView();
    }

    @GetMapping("/token")
    public void token(@RequestParam String state,
                              @RequestParam(value = "session_state") String sessionState,
                              @RequestParam String iss,
                              @RequestParam String code) throws HttpClientErrorException.BadRequest {
        if (matchingState(state)) {
            TokenResponse tokenResponse = keyCloakAuthorizationService.getAccessToken(code);
            String url = null;
        } else {
            throw new RuntimeException("State is not valid: " + state);
        }
    }

    private boolean matchingState(String state) {
        return state.equals(keyCloakConfigurationProperties.getRequest().get(AUTHORIZATION_CODE).getState());
    }
}
