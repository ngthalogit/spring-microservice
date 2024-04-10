package org.example.devicesweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenIdConfigurationProperties {
    private String issuer;
    private String authorizationEndpoint;
    private String tokenEndpoint;
    private String introspectionEndpoint;
    private String userInfoEndpoint;
    private String endSessionEndpoint;
    private String jwksUri;
}
