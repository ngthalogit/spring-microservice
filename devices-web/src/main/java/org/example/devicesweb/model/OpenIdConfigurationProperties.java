package org.example.devicesweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenIdConfigurationProperties {
    private String issuer;
    @JsonProperty("authorization_endpoint")
    private String authorizationEndpoint;
    @JsonProperty("token_endpoint")
    private String tokenEndpoint;
    @JsonProperty("introspection_endpoint")
    private String introspectionEndpoint;
    @JsonProperty("userinfo_endpoint")
    private String userInfoEndpoint;
    @JsonProperty("end_session_endpoint")
    private String endSessionEndpoint;
    @JsonProperty("jwks_uri")
    private String jwksUri;
}
