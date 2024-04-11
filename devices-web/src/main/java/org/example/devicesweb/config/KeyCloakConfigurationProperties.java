package org.example.devicesweb.config;

import lombok.Getter;
import lombok.Setter;
import org.example.devicesweb.model.KeyCloakRequestParameters;
import org.example.devicesweb.model.Realm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeyCloakConfigurationProperties {
    private Realm realm;
    private String clientId;
    private String clientSecret;
    private Map<String, KeyCloakRequestParameters> request;
}
