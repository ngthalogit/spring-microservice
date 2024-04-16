package org.example.devicesweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.devicesweb.config.KeyCloakConfigurationProperties;
import org.example.devicesweb.exception.UnAuthorizedAccessToken;
import org.example.devicesweb.model.TokenResponse;
import org.example.devicesweb.service.DeviceRequestService;
import org.example.devicesweb.service.KeyCloakAuthorizationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import static org.example.devicesweb.constant.KeyCloakTypeRequestProperties.AUTHORIZATION_CODE;


@Controller
@RequestMapping("/oauth2/grant/")
public class KeyCloakAuthorizationController {
    private final KeyCloakAuthorizationService keyCloakAuthorizationService;
    private final KeyCloakConfigurationProperties keyCloakConfigurationProperties;
    private final DeviceRequestService deviceRequestService;

    public KeyCloakAuthorizationController(KeyCloakAuthorizationService keyCloakAuthorizationService, KeyCloakConfigurationProperties keyCloakConfigurationProperties, DeviceRequestService deviceRequestService) {
        this.keyCloakAuthorizationService = keyCloakAuthorizationService;
        this.keyCloakConfigurationProperties = keyCloakConfigurationProperties;
        this.deviceRequestService = deviceRequestService;
    }

    @GetMapping("/auth")
    public RedirectView auth() {
        return keyCloakAuthorizationService.directAuthorizationCodeView();
    }

    @GetMapping("/token")
    public RedirectView token(@RequestParam String state,
                              @RequestParam(value = "session_state") String sessionState,
                              @RequestParam String iss,
                              @RequestParam String code) throws RuntimeException, UnAuthorizedAccessToken, JsonProcessingException {
        if (matchingState(state)) {
            TokenResponse tokenResponse = keyCloakAuthorizationService.getAccessToken(code);
            deviceRequestService.request(tokenResponse);
            return new RedirectView("http://localhost:8000/devices");
        } else {
            throw new RuntimeException("State is not valid: " + state);
        }
    }

    private boolean matchingState(String state) {
        return state.equals(keyCloakConfigurationProperties.getRequest().get(AUTHORIZATION_CODE).getState());
    }
}
