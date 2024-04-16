package org.example.devicesweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.devicesweb.config.KeyCloakConfigurationProperties;
import org.example.devicesweb.exception.UnAuthorizedAccessToken;
import org.example.devicesweb.model.KeyCloakRequestParameters;
import org.example.devicesweb.model.OpenIdConfigurationProperties;
import org.example.devicesweb.model.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.devicesweb.constant.GrantTypeRequestParameters.*;
import static org.example.devicesweb.constant.KeyCloakTypeRequestProperties.ACCESS_TOKEN;
import static org.example.devicesweb.constant.KeyCloakTypeRequestProperties.AUTHORIZATION_CODE;
import static org.example.devicesweb.constant.Mark.*;

@Service
public class KeyCloakAuthorizationServiceImpl implements KeyCloakAuthorizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyCloakAuthorizationServiceImpl.class);
    private final KeyCloakConfigurationProperties keyCloakConfigurationProperties;
    private final InvocationService invocationService;
    private final ObjectMapper objectMapper;
    private final OpenIdConfigurationProperties openIdConfigurationProperties;

    public KeyCloakAuthorizationServiceImpl(KeyCloakConfigurationProperties keyCloakConfigurationProperties, InvocationService invocationService, ObjectMapper objectMapper) {
        this.keyCloakConfigurationProperties = keyCloakConfigurationProperties;
        this.invocationService = invocationService;
        this.objectMapper = objectMapper;
        this.openIdConfigurationProperties = extract(keyCloakConfigurationProperties.getRealm().getConfigUrl());
    }

    @Override
    public RedirectView directAuthorizationCodeView() {
        Optional.ofNullable(keyCloakConfigurationProperties.getRequest())
                .orElseThrow(() -> new RuntimeException("Not found keycloak.request configuration properties"));
        Optional.ofNullable(keyCloakConfigurationProperties.getRequest().get(AUTHORIZATION_CODE))
                .orElseThrow(() -> new RuntimeException("Not found keycloak.request.authorization-code configuration properties"));

        KeyCloakRequestParameters keyCloakRequestParameters = keyCloakConfigurationProperties.getRequest().get(AUTHORIZATION_CODE);
        List<NameValuePair> parameters = this.getAuthorizationCodeRequestParameters(keyCloakRequestParameters);
        try {
            Optional.ofNullable(openIdConfigurationProperties.getAuthorizationEndpoint())
                    .orElseThrow(() -> new RuntimeException("Not found authorization_endpoint"));
            String uri = getAuthorizationCodeUri(openIdConfigurationProperties.getAuthorizationEndpoint(), parameters).toString();
            return new RedirectView(uri);
        } catch (URISyntaxException | RuntimeException e) {
            LOGGER.error("Error occurs when attempt to build uri {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public TokenResponse getAccessToken(String code) throws RuntimeException, UnAuthorizedAccessToken {
        Optional.ofNullable(keyCloakConfigurationProperties.getRequest())
                .orElseThrow(() -> new RuntimeException("Not found keycloak.request configuration properties"));
        Optional.ofNullable(keyCloakConfigurationProperties.getRequest().get(ACCESS_TOKEN))
                .orElseThrow(() -> new RuntimeException("Not found keycloak.request.access-token configuration properties"));

        KeyCloakRequestParameters keyCloakRequestParameters = keyCloakConfigurationProperties.getRequest().get(ACCESS_TOKEN);
        List<NameValuePair> parameters = this.getAccessTokenRequestParameters(keyCloakRequestParameters, code);
        try {
            HttpResponse response = invocationService.execute(
                    HttpPost.METHOD_NAME,
                    openIdConfigurationProperties.getTokenEndpoint(),
                    parameters
            );
            if (response.getCode() == 200) {
                return objectMapper.readValue(response.getReasonPhrase(), TokenResponse.class);
            }
            throw new RuntimeException(response.getReasonPhrase());

        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurs when attempt to extract openid config {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private List<NameValuePair> getAuthorizationCodeRequestParameters(KeyCloakRequestParameters keyCloakRequestParameters) {
        return new ArrayList<>() {
            {
                add(new BasicNameValuePair(CLIENT_ID, keyCloakConfigurationProperties.getClientId()));
                add(new BasicNameValuePair(RESPONSE_TYPE, keyCloakRequestParameters.getResponseType()));
                add(new BasicNameValuePair(SCOPE, keyCloakRequestParameters.getScope()));
                add(new BasicNameValuePair(REDIRECT_URI, keyCloakRequestParameters.getRedirectUri()));
                add(new BasicNameValuePair(STATE, keyCloakRequestParameters.getState()));
            }
        };
    }


    private List<NameValuePair> getAccessTokenRequestParameters(KeyCloakRequestParameters keyCloakRequestParameters, String code) {
        return new ArrayList<>() {
            {
                add(new BasicNameValuePair(CLIENT_ID, keyCloakConfigurationProperties.getClientId()));
                add(new BasicNameValuePair(CLIENT_SECRET, keyCloakConfigurationProperties.getClientSecret()));
                add(new BasicNameValuePair(GRANT_TYPE, keyCloakRequestParameters.getGrantType()));
                add(new BasicNameValuePair(CODE, code));
                add(new BasicNameValuePair(REDIRECT_URI, keyCloakRequestParameters.getRedirectUri()));
                add(new BasicNameValuePair(SCOPE, keyCloakRequestParameters.getScope()));
            }
        };
    }


    private URI getAuthorizationCodeUri(String url, List<NameValuePair> parameters) throws URISyntaxException {
        url += QUESTION_MARK;
        for (int i = 0; i < parameters.size(); i++) {
            NameValuePair parameter = parameters.get(i);
            url += parameter.getName() + EQUALS_MARK + parameter.getValue();
            if (i < parameters.size() - 1) {
                url += AMPERSAND_MARK;
            }
        }
        return new URI(url);
    }


    private OpenIdConfigurationProperties extract(String configUrl) {
        try {
            HttpResponse response = invocationService.execute(
                    HttpGet.METHOD_NAME,
                    configUrl,
                    null
            );
            if (response.getCode() == 200) {
                return objectMapper.readValue(response.getReasonPhrase(), OpenIdConfigurationProperties.class);
            }
            throw new RuntimeException(response.getReasonPhrase());
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurs when attempt to extract openid config {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
