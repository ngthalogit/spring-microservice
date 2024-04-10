package org.example.devicesweb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeyCloakConfigurationProperties {
    private String configUrl;
    private String clientId;
    private String clientSecret;
}
