package org.example.devicesweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeyCloakRequestParameters {
    private String scope;
    private String responseType;
    private String redirectUri;
    private String state;
    private String grantType;
    private String code;
}
